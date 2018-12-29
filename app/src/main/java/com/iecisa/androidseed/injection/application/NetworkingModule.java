package com.iecisa.androidseed.injection.application;

import com.iecisa.androidseed.api.MarvelApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {
    private static final String BASE_URL = "https://api.myjson.com";

    @Singleton
    @Provides
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    MarvelApi getMarvelApi(Retrofit retrofit) {
        return retrofit.create(MarvelApi.class);
    }
}
