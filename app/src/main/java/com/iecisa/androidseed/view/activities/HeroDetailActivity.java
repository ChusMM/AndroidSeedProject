package com.iecisa.androidseed.view.activities;

import android.os.Bundle;

import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.domain.usecases.FetchHeroesUseCase;
import com.iecisa.androidseed.injection.BaseActivity;
import com.iecisa.androidseed.mvc.heroes.HeroDetailViewMvc;
import com.iecisa.androidseed.mvc.viewmvc.ViewMvcFactory;
import com.iecisa.androidseed.view.dialogs.DialogsManager;

import javax.inject.Inject;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 4/1/19.
 * email: jmanuel_munoz@iecisa.com
 */

public class HeroDetailActivity extends BaseActivity implements HeroDetailViewMvc.Listener {
    private static final String TAG = HeroDetailActivity.class.getSimpleName();

    public static final String HERO_EXTRA = "hero_extra";

    @Inject
    FetchHeroesUseCase mFetchHeroesUseCase;

    @Inject
    DialogsManager mDialogsManager;

    @Inject
    ViewMvcFactory mViewMvcFactory;

    private HeroDetailViewMvc mViewMvc;
    private SuperHero mHeroBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);

        mViewMvc = mViewMvcFactory.newInstance(HeroDetailViewMvc.class, null);
        setContentView(mViewMvc.getRootView());

        mHeroBound = getIntent().getParcelableExtra(HERO_EXTRA);
    }

    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    public void onResume() {
        super.onResume();
        mViewMvc.bindHeroDetail(mHeroBound);
    }

    @Override
    public void onPictureClicked() {

    }
}
