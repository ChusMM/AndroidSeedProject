package com.iecisa.androidseed.mvc.heroes;

import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.viewmvc.ObservableViewMvc;

import java.util.List;

public interface HeroesListViewMvc extends ObservableViewMvc<HeroesListViewMvc.Listener> {

    interface Listener {
        void onHeroClicked(SuperHero superHero);
    }

    void onRefresh();
    void bindHeroes(List<SuperHero> superHeroes, Listener listener);
}
