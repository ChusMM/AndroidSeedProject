package com.iecisa.androidseed.injection.application;

import android.app.Application;

import com.iecisa.androidseed.api.MarvelApi;
import com.iecisa.androidseed.domain.usecases.FetchHeroesUseCase;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    FetchHeroesUseCase getFetchHeroesUseCase(MarvelApi marvelApi) {
        return new FetchHeroesUseCase(marvelApi, mApplication.getApplicationContext());
    }
}
