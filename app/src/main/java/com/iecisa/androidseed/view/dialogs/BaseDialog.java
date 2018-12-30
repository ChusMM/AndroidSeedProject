package com.iecisa.androidseed.view.dialogs;

import android.support.annotation.UiThread;
import android.support.v4.app.DialogFragment;

import com.iecisa.androidseed.App;
import com.iecisa.androidseed.injection.application.ApplicationComponent;
import com.iecisa.androidseed.injection.presentation.PresentationComponent;
import com.iecisa.androidseed.injection.presentation.PresentationModule;

public abstract class BaseDialog extends DialogFragment {

    private boolean mIsInjectorUsed;

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule(getActivity()));

    }

    private ApplicationComponent getApplicationComponent() {
        if (getActivity() == null) {
            throw new RuntimeException("DialogFragment linked activity not instantiated");
        }

        return ((App) getActivity().getApplication()).getApplicationComponent();
    }
}
