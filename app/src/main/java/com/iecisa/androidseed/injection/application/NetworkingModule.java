package com.iecisa.androidseed.injection.application;

import com.techyourchance.journeytodependencyinjection.Constants;
import com.techyourchance.journeytodependencyinjection.networking.StackoverflowApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {

    @Singleton
    @Provides
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    StackoverflowApi getStackoverflowApi(Retrofit retrofit) {
        return retrofit.create(StackoverflowApi.class);
    }

}
