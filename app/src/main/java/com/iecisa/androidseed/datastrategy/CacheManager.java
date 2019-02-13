package com.iecisa.androidseed.datastrategy;

import android.content.Context;
import androidx.annotation.NonNull;

import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.persistence.AppDatabase;

import java.util.List;

public class CacheManager {
    private AppDatabase appDatabase;
    private Context context;

    public CacheManager(AppDatabase appDatabase, Context context) {
        this.appDatabase = appDatabase;
        this.context = context;
    }

    public void listHeroes(@NonNull DataStrategy.HeroesListener listener) {
        List<SuperHero> superHeroes = appDatabase.heroDao().getAll();
        listener.onQueryHeroesOk(superHeroes);
    }

    public List<SuperHero> listHeroes() {
        return appDatabase.heroDao().getAll();
    }

    public void deleteAllHeroes() {
        for (SuperHero superHero : listHeroes()) {
            appDatabase.heroDao().delete(superHero);
        }
    }

    public void saveHeroes(List<SuperHero> superHeroes) {
        appDatabase.heroDao().insertAll(superHeroes);
    }
}
