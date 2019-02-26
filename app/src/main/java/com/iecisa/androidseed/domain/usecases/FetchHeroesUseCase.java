package com.iecisa.androidseed.domain.usecases;

import android.content.Context;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.datastrategy.DataStrategy;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.injection.BaseUseCase;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 31/01/2019.
 * email: jmanuel_munoz@iecisa.com
 */

public class FetchHeroesUseCase extends BaseUseCase<FetchHeroesUseCase.Listener>
        implements DataStrategy.HeroesListener {

    public interface Listener {
        void onFetchHeroesOk(List<SuperHero> superHeroes);
        void onFetchHeroesFailed(String msg);
    }

    private final DataStrategy dataStrategy;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FetchHeroesUseCase(DataStrategy dataStrategy,
                              Context context) {

        this.dataStrategy = dataStrategy;
        super.setContextRef(context);
    }

    public void fetchAndNotify() {
        dataStrategy.queryHeroes(this);
    }

    @Override
    public void onQueryHeroesOk(List<SuperHero> superHeroes) {
        this.notifySucceeded(superHeroes);
    }

    @Override
    public void onQueryHeroesFailed() {
        this.notifyFailed();
    }

    protected void notifySucceeded(List<SuperHero> superHeroes) {
        Disposable disposable = Observable.fromIterable(getListeners())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener -> listener.onFetchHeroesOk(superHeroes));

        compositeDisposable.add(disposable);
    }

    protected void notifyFailed() {
        final String reason;
        if (getContextRef() != null) {
            reason = getContextRef().getString(R.string.call_failed);
        } else {
            reason = "Connection Failed";
        }

        Disposable disposable = Flowable.fromIterable(getListeners())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener -> listener.onFetchHeroesFailed(reason));

        compositeDisposable.add(disposable);
    }
}
