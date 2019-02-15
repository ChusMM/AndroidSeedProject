package com.iecisa.androidseed.datastrategy;

import android.content.Context;
import androidx.annotation.NonNull;

import com.iecisa.androidseed.api.HeroListWrapper;
import com.iecisa.androidseed.api.MarvelApi;
import com.iecisa.androidseed.domain.SuperHero;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 31/01/2019.
 * email: jmanuel_munoz@iecisa.com
 */
public class DataWebService extends DataStrategy implements CacheManager.CacheListener {
    private Call<HeroListWrapper> mCall;

    public DataWebService(MarvelApi marvelApi, CacheManager cacheManager, DataFactory dataFactory, Context context) {
        super(marvelApi, cacheManager, dataFactory, context);
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
                    saveHeroes(result, DataWebService.this);
                    listener.onQueryHeroesOk(result);
                } else {
                    cacheManager.listHeroes(listener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HeroListWrapper> call,
                                  @NonNull Throwable t) {
                cacheManager.listHeroes(listener);
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    @Override
    public void onOperationFinish() {

    }
}
