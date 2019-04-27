package com.iecisa.androidseed.datastrategy;

import android.content.Context;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.persistence.SuperHeroDao;

import java.util.List;

public class CacheManager {
    private final SuperHeroDao superHeroDao;
    private final Context context;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public interface CacheListener {
        void onOperationFinish(int rowsAffected);
    }

    public CacheManager(SuperHeroDao superHeroDao, Context context) {
        this.superHeroDao = superHeroDao;
        this.context = context;
    }

    public void listHeroes(@NonNull DataStrategy.HeroesListener listener) {
        Disposable disposable = superHeroDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onQueryHeroesOk);
        compositeDisposable.add(disposable);
    }

    public void deleteAllHeroes(CacheListener listener) {
        Disposable disposable = superHeroDao.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onOperationFinish);
        compositeDisposable.add(disposable);
    }

    public void replaceHeroes(List<SuperHero> superHeroes, CacheListener listener) {
        Disposable disposable = superHeroDao.insertAll(superHeroes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    listener.onOperationFinish(0);
                });

        compositeDisposable.add(disposable);
    }
}
