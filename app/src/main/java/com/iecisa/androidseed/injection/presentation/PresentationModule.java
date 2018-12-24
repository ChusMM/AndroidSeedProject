package com.iecisa.androidseed.injection.presentation;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.techyourchance.journeytodependencyinjection.networking.StackoverflowApi;
import com.techyourchance.journeytodependencyinjection.questions.FetchQuestionDetailsUseCase;
import com.techyourchance.journeytodependencyinjection.screens.common.ImageLoader;
import com.techyourchance.journeytodependencyinjection.screens.common.dialogs.DialogsManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    private final FragmentActivity mActivity;

    public PresentationModule(FragmentActivity fragmentActivity) {
        mActivity = fragmentActivity;
    }

    @Provides
    FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    @Provides
    Activity getActivity() {
        return mActivity;
    }

    @Provides
    Context context(Activity activity) {
        return activity;
    }
    
    @Provides
    FetchHeroDetailUseCase getFetchQuestionDetailsUseCase(MarvelApi stackoverflowApi) {
        return new FetchQuestionDetailsUseCase(stackoverflowApi);
    }

    @Provides
    DialogsManager getDialogsManager(FragmentManager fragmentManager) {
        return new DialogsManager(fragmentManager);
    }

    @Provides
    ImageLoader getImageLoader(Activity activity) {
        return new ImageLoader(activity);
    }
}
