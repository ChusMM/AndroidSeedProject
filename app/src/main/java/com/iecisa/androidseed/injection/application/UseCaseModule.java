package com.iecisa.androidseed.injection.application;

import android.content.Context;

import com.iecisa.androidseed.BuildConfig;
import com.iecisa.androidseed.api.MarvelApi;
import com.iecisa.androidseed.datastrategy.DataFactory;
import com.iecisa.androidseed.datastrategy.DataSource;
import com.iecisa.androidseed.datastrategy.DataStrategy;
import com.iecisa.androidseed.persistence.AppDatabase;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UseCaseModule {
    private static final String BASE_URL = BuildConfig.API_BASE_URL;

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

    @Singleton
    @Provides
    DataFactory getDataFactory() {
        return new DataFactory();
    }

    @Singleton
    @Provides
    DataSource getDataSource() {
        return DataSource.getDefaulDataSource();
    }

    @Singleton
    @Provides
    AppDatabase getAppDataBase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "heroes_database").build();
    }

    @Provides
    DataStrategy getDataStrategy(DataSource dataSource,
                                 MarvelApi marvelApi,
                                 AppDatabase appDatabase,
                                 DataFactory dataFactory) {
        return DataStrategy.newInstance(dataSource, marvelApi, appDatabase, dataFactory);
    }
}
