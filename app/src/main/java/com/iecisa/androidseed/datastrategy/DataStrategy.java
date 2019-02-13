package com.iecisa.androidseed.datastrategy;

import android.content.Context;
import androidx.annotation.NonNull;

import com.iecisa.androidseed.api.MarvelApi;
import com.iecisa.androidseed.domain.SuperHero;

import java.util.List;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 31/01/2019.
 * email: jmanuel_munoz@iecisa.com
 */

public abstract class DataStrategy {
    protected MarvelApi marvelApi;
    protected CacheManager cacheManager;
    protected DataFactory dataFactory;
    protected Context context;

    public DataStrategy(MarvelApi marvelApi, CacheManager cacheManager,
                        DataFactory dataFactory, Context context) {
        this.marvelApi = marvelApi;
        this.cacheManager = cacheManager;
        this.dataFactory = dataFactory;
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

    protected void deleteAllHeroes() {
        this.cacheManager.deleteAllHeroes();
    }

    protected void saveHeroes(List<SuperHero> superHeroes) {
        this.deleteAllHeroes();
        this.cacheManager.saveHeroes(superHeroes);
    }

    public static DataStrategy newInstance(DataSource dataSource,
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
