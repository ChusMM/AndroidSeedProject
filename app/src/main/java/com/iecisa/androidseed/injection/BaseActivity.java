package com.iecisa.androidseed.injection;

import android.annotation.SuppressLint;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.iecisa.androidseed.App;
import com.iecisa.androidseed.injection.application.ApplicationComponent;
import com.iecisa.androidseed.injection.presentation.PresentationComponent;
import com.iecisa.androidseed.injection.presentation.PresentationModule;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private boolean mIsInjectorUsed;

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule(this));

    }

    private ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }
}
