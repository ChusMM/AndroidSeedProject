package com.iecisa.androidseed.injection;

import android.app.Service;
import androidx.annotation.UiThread;

import com.iecisa.androidseed.App;
import com.iecisa.androidseed.injection.application.ApplicationComponent;
import com.iecisa.androidseed.injection.service.ServiceComponent;
import com.iecisa.androidseed.injection.service.ServiceModule;

public abstract class BaseService extends Service {

    private boolean mIsServiceComponentUsed;

    @UiThread
    protected ServiceComponent getServiceComponent() {
        if (mIsServiceComponentUsed) {
            throw new RuntimeException("there is no reason to perform injection more than once");
        }

        mIsServiceComponentUsed = true;

        return getApplicationComponent().newServiceComponent(new ServiceModule(this));
    }

    private ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }
}
