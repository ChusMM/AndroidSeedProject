package com.iecisa.androidseed.domain.usecases;

import android.content.Context;
import android.support.annotation.NonNull;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.api.HeroListWrapper;
import com.iecisa.androidseed.api.MarvelApi;
import com.iecisa.androidseed.datastrategy.DataFactory;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.injection.BaseUseCase;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchHeroesUseCase extends BaseUseCase<FetchHeroesUseCase.Listener, HeroListWrapper> {

    public interface Listener {
        void onFetchHeroesOk(List<SuperHero> superHeroes);
        void onFetchHeroesFailed(String msg);
    }

    private MarvelApi mMarvelApi;

    @Inject Context context;

    public FetchHeroesUseCase(MarvelApi marvelApi) {
        this.mMarvelApi = marvelApi;

        getUseCaseComponent().inject(this);

        super.setContextRef(context);
    }

    public void fetchAndNotify() {
        this.cancelCurrentFetchIfActive();

        mCall = mMarvelApi.getHeroes();
        if (mCall == null) {
            notifyFailed();
            return;
        }

        mCall.enqueue(new Callback<HeroListWrapper>() {
            @Override
            public void onResponse(@NonNull Call<HeroListWrapper> call,
                                   @NonNull Response<HeroListWrapper> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(DataFactory.superHeroesFromHeroListWrapper(response.body()));
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HeroListWrapper> call,
                                  @NonNull Throwable t) {
                notifyFailed();
            }
        });
    }

    protected void notifySucceeded(List<SuperHero> superHeroes) {
        List<SuperHero> unmodifiableQuestions = Collections.unmodifiableList(superHeroes);
        for (FetchHeroesUseCase.Listener listener : getListeners()) {
            listener.onFetchHeroesOk(unmodifiableQuestions);
        }
    }

    protected void notifyFailed() {
        final String reason;
        if (getContextRef() != null) {
            reason = getContextRef().getString(R.string.call_failed);
        } else {
            reason = "Connection Failed";
        }

        for (FetchHeroesUseCase.Listener listener : getListeners()) {
            listener.onFetchHeroesFailed(reason);
        }
    }
}
