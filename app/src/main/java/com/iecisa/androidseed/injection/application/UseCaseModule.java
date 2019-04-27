package com.iecisa.androidseed.injection.application;

import android.app.Application;
import android.content.Context;

import com.iecisa.androidseed.BuildConfig;
import com.iecisa.androidseed.api.MarvelApi;
import com.iecisa.androidseed.datastrategy.CacheManager;
import com.iecisa.androidseed.datastrategy.DataFactory;
import com.iecisa.androidseed.datastrategy.DataMock;
import com.iecisa.androidseed.datastrategy.DataSource;
import com.iecisa.androidseed.datastrategy.DataStrategy;
import com.iecisa.androidseed.datastrategy.DataWebService;
import com.iecisa.androidseed.persistence.AppDatabase;
import com.iecisa.androidseed.persistence.SuperHeroDao;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
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
    AppDatabase getAppDataBase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "heroes_database").build();
    }

    @Singleton
    @Provides
    SuperHeroDao getSuperHeroDao(AppDatabase appDatabase) {
        return appDatabase.heroDao();
    }

    @Provides
    CacheManager getCacheManager(SuperHeroDao superHeroDao, Context context) {
        return new CacheManager(superHeroDao, context);
    }

    @Provides
    DataStrategy getDataStrategy(DataSource dataSource,
                                 MarvelApi marvelApi,
                                 CacheManager cacheManager,
                                 DataFactory dataFactory,
                                 Context context) {
        switch (dataSource) {
            case DATA_WS:
                return new DataWebService(marvelApi, cacheManager, dataFactory, context);
            case DATA_MOCK:
                return new DataMock(dataFactory, context);
            default:
                return new DataWebService(marvelApi, cacheManager, dataFactory, context);
        }
    }
}
