package com.iecisa.androidseed;

import android.app.Application;

import com.iecisa.androidseed.injection.application.ApplicationComponent;
import com.iecisa.androidseed.injection.application.ApplicationModule;
import com.iecisa.androidseed.injection.application.DaggerApplicationComponent;
import com.iecisa.androidseed.injection.application.UseCaseModule;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 23/12/18.
 * email: jmanuel_munoz@iecisa.com
 */

public class App extends Application {
    private static App instance;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mApplicationComponent =  DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .useCaseModule(new UseCaseModule())
                .build();
    }

    public static App getInstance() {
        if (instance == null) {
            throw new RuntimeException("App not instanced");
        }
        return instance;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
