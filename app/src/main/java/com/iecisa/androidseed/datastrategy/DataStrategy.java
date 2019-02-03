package com.iecisa.androidseed.datastrategy;

import android.content.Context;
import androidx.annotation.NonNull;

import com.iecisa.androidseed.api.MarvelApi;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.persistence.AppDatabase;

import java.util.List;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 31/01/2019.
 * email: jmanuel_munoz@iecisa.com
 */

public abstract class DataStrategy {
    protected MarvelApi marvelApi;
    protected AppDatabase appDatabase;
    protected DataFactory dataFactory;
    protected Context context;

    public DataStrategy(MarvelApi marvelApi, DataFactory dataFactory, Context context) {
        this.marvelApi = marvelApi;
        this.dataFactory = dataFactory;
        this.context = context;
    }

    public DataStrategy(AppDatabase appDatabase, Context context) {
        this.appDatabase = appDatabase;
        this.context = context;
    }

    public DataStrategy(DataFactory dataFactory, Context context) {
        this.dataFactory = dataFactory;
        this.context = context;
    }

    public interface HeroesListener {
        void onQueryHeroesOk(List<SuperHero> superHeroes);
        void onQueryHeroesFailed();
    }

    public abstract void queryHeroes(@NonNull HeroesListener listener);
    protected abstract void deleteAllHeroes();
    protected abstract void saveHeroes(List<SuperHero> superHeroes);

    protected DataStrategy getCacheManager(AppDatabase appDatabase) {
        return new DataLocal(appDatabase, context);
    }

    public static DataStrategy newInstance(DataSource dataSource,
                                           MarvelApi marvelApi,
                                           AppDatabase appDatabase,
                                           DataFactory dataFactory,
                                           Context context) {
        switch (dataSource) {
            case DATA_WS:
                return new DataWebService(marvelApi, appDatabase, dataFactory, context);
            case DATA_DB:
                return new DataLocal(appDatabase, context);
            case DATA_MOCK:
                return new DataMock(dataFactory, context);
            default:
                return new DataWebService(marvelApi, appDatabase, dataFactory, context);
        }
    }
}
