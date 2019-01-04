package com.iecisa.androidseed.injection.presentation;

import com.iecisa.androidseed.injection.BaseActivity;
import com.iecisa.androidseed.view.adapters.BaseViewHolder;

import dagger.Subcomponent;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {
    void inject(BaseActivity questionsListActivity);
    void inject(BaseViewHolder heroHolder);
}
