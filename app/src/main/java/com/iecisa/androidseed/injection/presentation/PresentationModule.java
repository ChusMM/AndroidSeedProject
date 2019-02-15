package com.iecisa.androidseed.injection.presentation;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;

import com.iecisa.androidseed.mvc.viewmvc.ViewMvcFactory;
import com.iecisa.androidseed.util.ImageLoader;
import com.iecisa.androidseed.util.ImageUtils;
import com.iecisa.androidseed.view.dialogs.DialogsManager;

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
    DialogsManager getDialogsManager(FragmentManager fragmentManager) {
        return new DialogsManager(fragmentManager);
    }

    @Provides
    ImageLoader getImageLoader() {
        return new ImageLoader();
    }

    @Provides
    ImageUtils getImageUtils(Activity activity) {
        return new ImageUtils(activity);
    }

    @Provides
    ViewMvcFactory getViewMvcFactory(LayoutInflater layoutInflater,
                                     ImageLoader imageLoader, ImageUtils imageUtils) {
        return new ViewMvcFactory(layoutInflater, imageLoader, imageUtils);
    }
}
