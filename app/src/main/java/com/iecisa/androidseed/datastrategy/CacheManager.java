package com.iecisa.androidseed.datastrategy;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.persistence.SuperHeroDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class CacheManager {
    private final SuperHeroDao superHeroDao;
    private final Context context;
    private final ExecutorService executorService;
    private final Handler uiHandler;

    public interface CacheListener {
        void onOperationFinish();
    }

    public CacheManager(SuperHeroDao superHeroDao, Context context) {
        this.superHeroDao = superHeroDao;
        this.context = context;
        this.executorService = Executors.newSingleThreadExecutor();
        this.uiHandler = new Handler(Looper.getMainLooper());
    }

    public void listHeroes(@NonNull DataStrategy.HeroesListener listener) {
        executorService.execute(() -> {
            List<SuperHero> superHeroes = superHeroDao.getAll();
            onHeroesListFinished(listener, superHeroes);
        });
    }

    @WorkerThread
    private List<SuperHero> listHeroes() {
        return superHeroDao.getAll();
    }

    public void deleteAllHeroes(CacheListener listener) {
        executorService.execute(() -> {
            for (SuperHero superHero : listHeroes()) {
                superHeroDao.delete(superHero);
                uiHandler.post(listener::onOperationFinish);
            }
        });
    }

    public void replaceHeroes(List<SuperHero> superHeroes, CacheListener listener) {
        executorService.execute(() -> {
            superHeroDao.insertAll(superHeroes);
            uiHandler.post(listener::onOperationFinish);
        });
    }

    private void onHeroesListFinished(DataStrategy.HeroesListener listener,
                                      List<SuperHero> superHeroes) {
        this.uiHandler.post(() -> listener.onQueryHeroesOk(superHeroes));
    }
}
