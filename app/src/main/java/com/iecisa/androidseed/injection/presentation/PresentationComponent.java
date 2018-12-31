package com.iecisa.androidseed.injection.presentation;

import com.iecisa.androidseed.view.activities.HeroesListActivity;
import com.iecisa.androidseed.view.adapters.BaseViewHolder;

import dagger.Subcomponent;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {
    void inject(HeroesListActivity questionsListActivity);
    void inject(BaseViewHolder heroHolder);
}
