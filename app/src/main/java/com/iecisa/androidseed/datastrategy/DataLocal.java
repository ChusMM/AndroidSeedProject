package com.iecisa.androidseed.datastrategy;

import android.content.Context;
import androidx.annotation.NonNull;

import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.persistence.AppDatabase;

import java.util.List;

public class DataLocal extends DataStrategy {
    public DataLocal(AppDatabase appDatabase, Context context) {
        super(appDatabase, context);
    }

    @Override
    public void queryHeroes(@NonNull HeroesListener listener) {
        List<SuperHero> superHeroes = super.appDatabase.heroDao().getAll();
        listener.onQueryHeroesOk(superHeroes);
    }

    @Override
    protected void deleteAllHeroes() {
        super.appDatabase.heroDao().delete();
    }

    @Override
    protected void saveHeroes(List<SuperHero> superHeroes) {
        super.appDatabase.heroDao().insertAll(superHeroes);
    }
}
