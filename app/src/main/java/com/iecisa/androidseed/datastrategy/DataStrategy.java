package com.iecisa.androidseed.datastrategy;

import android.support.annotation.NonNull;

import com.iecisa.androidseed.api.MarvelApi;
import com.iecisa.androidseed.domain.SuperHero;

import java.util.List;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 31/01/2019.
 * email: jmanuel_munoz@iecisa.com
 */

public abstract class DataStrategy {
    protected MarvelApi mMarvelApi;
    protected DataFactory dataFactory;

    public DataStrategy(MarvelApi marvelApi, DataFactory dataFactory) {
        this.mMarvelApi = marvelApi;
        this.dataFactory = dataFactory;
    }

    public interface HeroesListener {
        void onQueryHeroesOk(List<SuperHero> superHeroes);
        void onQueryHeroesFailed();
    }

    public abstract void queryHeroes(@NonNull HeroesListener listener);

    public static DataStrategy newInstance(DataSource dataSource,
                                           MarvelApi marvelApi,
                                           DataFactory dataFactory) {
        switch (dataSource) {
            case DATA_WS:
            case DATA_DB:
            case DATA_MOCK:
            default:
                return new DataWebService(marvelApi, dataFactory);
        }
    }
}
