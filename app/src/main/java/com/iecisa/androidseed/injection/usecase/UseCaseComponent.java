package com.iecisa.androidseed.injection.usecase;

import com.iecisa.androidseed.domain.usecases.FetchHeroesUseCase;

import dagger.Subcomponent;

@Subcomponent (modules = UseCaseModule.class)
public interface UseCaseComponent {
    void inject(FetchHeroesUseCase baseUseCase);
}