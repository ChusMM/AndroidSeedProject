package com.iecisa.androidseed.datastrategy;

import android.content.Context;
import android.support.annotation.NonNull;

import com.iecisa.androidseed.api.HeroListWrapper;
import com.iecisa.androidseed.api.MarvelApi;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.persistence.AppDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 31/01/2019.
 * email: jmanuel_munoz@iecisa.com
 */
public class DataWebService extends DataStrategy {
    private Call<HeroListWrapper> mCall;
    private DataStrategy cacheManager;

    public DataWebService(MarvelApi marvelApi, AppDatabase appDatabase, DataFactory dataFactory, Context context) {
        super(marvelApi, dataFactory, context);
        this.cacheManager = super.getCacheManager(appDatabase);
    }

    @Override
    public void queryHeroes(@NonNull final HeroesListener listener) {
        this.cancelCurrentFetchIfActive();

        mCall = marvelApi.getHeroes();
        if (mCall == null) {
            listener.onQueryHeroesFailed();
            return;
        }

        mCall.enqueue(new Callback<HeroListWrapper>() {
            @Override
            public void onResponse(@NonNull Call<HeroListWrapper> call,
                                   @NonNull Response<HeroListWrapper> response) {
                if (response.isSuccessful()) {
                    List<SuperHero> result = dataFactory.superHeroesFromHeroListWrapper(response.body());
                    saveHeroes(result);
                    listener.onQueryHeroesOk(result);
                } else {
                    listener.onQueryHeroesFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HeroListWrapper> call,
                                  @NonNull Throwable t) {
                listener.onQueryHeroesFailed();
            }
        });
    }

    @Override
    protected void deleteAllHeroes() {
        this.cacheManager.deleteAllHeroes();
    }

    @Override
    protected void saveHeroes(List<SuperHero> superHeroes) {
        this.deleteAllHeroes();
        this.cacheManager.saveHeroes(superHeroes);
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }
}
