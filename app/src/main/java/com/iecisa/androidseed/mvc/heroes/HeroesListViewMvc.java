package com.iecisa.androidseed.mvc.heroes;

import android.support.v4.widget.SwipeRefreshLayout;

import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.viewmvc.ObservableViewMvc;

import java.util.List;

public interface HeroesListViewMvc extends ObservableViewMvc<HeroesListViewMvc.Listener> {

    interface Listener {
        void onHeroClicked(SuperHero superHero);
    }

    void unbind();
    void onViewRefresh();
    void bindSwipeRefresh(SwipeRefreshLayout.OnRefreshListener listener);
    void bindHeroes(List<SuperHero> superHeroes, Listener listener);
}
