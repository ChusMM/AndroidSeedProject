package com.iecisa.androidseed.injection.application;

import com.iecisa.androidseed.api.MarvelApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {
    private static final String BASE_URL = "https://api.myjson.com";

    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 10;

    @Singleton
    @Provides
    Retrofit.Builder getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
    }

    @Singleton
    @Provides
    MarvelApi getMarvelApi(Retrofit.Builder retrofitBuilder) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = retrofitBuilder.client(client).build();

        return retrofit.create(MarvelApi.class);
    }
}
