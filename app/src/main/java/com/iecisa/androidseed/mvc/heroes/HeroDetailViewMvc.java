package com.iecisa.androidseed.mvc.heroes;

import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.viewmvc.ObservableViewMvc;

public interface HeroDetailViewMvc extends ObservableViewMvc<HeroDetailViewMvc.Listener> {

    interface Listener {
        void onPictureClicked();
    }

    void bindHeroDetail(SuperHero hero);
}
