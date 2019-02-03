package com.iecisa.androidseed.domain.usecases;

import android.content.Context;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.datastrategy.DataStrategy;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.injection.BaseUseCase;

import java.util.Collections;
import java.util.List;

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

    private DataStrategy dataStrategy;

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
        List<SuperHero> unmodifiableQuestions = Collections.unmodifiableList(superHeroes);
        for (FetchHeroesUseCase.Listener listener : getListeners()) {
            listener.onFetchHeroesOk(unmodifiableQuestions);
        }
    }

    protected void notifyFailed() {
        final String reason;
        if (getContextRef() != null) {
            reason = getContextRef().getString(R.string.call_failed);
        } else {
            reason = "Connection Failed";
        }

        for (FetchHeroesUseCase.Listener listener : getListeners()) {
            listener.onFetchHeroesFailed(reason);
        }
    }
}
