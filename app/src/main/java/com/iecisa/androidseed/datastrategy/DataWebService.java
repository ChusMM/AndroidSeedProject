package com.iecisa.androidseed.datastrategy;

import android.content.Context;
import androidx.annotation.NonNull;

import com.iecisa.androidseed.api.HeroListWrapper;
import com.iecisa.androidseed.api.MarvelApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 31/01/2019.
 * email: jmanuel_munoz@iecisa.com
 */

public class DataWebService extends DataStrategy implements CacheManager.CacheListener {

    private Disposable disposable;

    public DataWebService(MarvelApi marvelApi, CacheManager cacheManager, DataFactory dataFactory, Context context) {
        super(marvelApi, cacheManager, dataFactory, context);
    }

    @Override
    public void queryHeroes(@NonNull final HeroesListener listener) {
        this.cancelCurrentFetchIfActive();

        Observable<HeroListWrapper> mHeroObservable = marvelApi.getHeroes();
        if (mHeroObservable == null) {
            listener.onQueryHeroesFailed();
            return;
        }

        this.cancelCurrentFetchIfActive();

        disposable = mHeroObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> dataFactory.superHeroesFromHeroListWrapper(result))
                .subscribe (result -> {
                            cacheManager.replaceHeroes(result, DataWebService.this);
                            listener.onQueryHeroesOk(result);
                        },
                        throwable -> cacheManager.listHeroes(listener));
    }

    private void cancelCurrentFetchIfActive() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onOperationFinish(int rowsAffected) {  }
}
