package com.iecisa.androidseed;

import android.app.Application;

import com.iecisa.androidseed.injection.application.ApplicationComponent;
import com.iecisa.androidseed.injection.application.ApplicationModule;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 23/12/18.
 * email: jmanuel_munoz@iecisa.com
 */

public class App extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
