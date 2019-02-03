package com.iecisa.androidseed.injection;

import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import com.iecisa.androidseed.App;
import com.iecisa.androidseed.injection.application.ApplicationComponent;
import com.iecisa.androidseed.injection.presentation.PresentationComponent;
import com.iecisa.androidseed.injection.presentation.PresentationModule;

public abstract class BaseFragment extends Fragment {

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
            throw new RuntimeException("Fragment linked activity not instantiated");
        }

        return ((App) getActivity().getApplication()).getApplicationComponent();
    }
}
