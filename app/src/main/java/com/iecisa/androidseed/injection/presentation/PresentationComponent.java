package com.iecisa.androidseed.injection.presentation;


import dagger.Subcomponent;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {
    void inject(HeroesListActivity questionsListActivity);
    void inject(HeroDetailActivity questionDetailsActivity);
}
