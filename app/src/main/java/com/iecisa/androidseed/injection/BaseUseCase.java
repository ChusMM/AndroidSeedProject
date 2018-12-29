package com.iecisa.androidseed.injection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.iecisa.androidseed.App;
import com.iecisa.androidseed.injection.application.ApplicationComponent;
import com.iecisa.androidseed.injection.usecase.UseCaseModule;
import com.iecisa.androidseed.injection.usecase.UseCaseComponent;
import com.iecisa.androidseed.mvc.BaseObservable;

import java.lang.ref.WeakReference;

public class BaseUseCase<L> extends BaseObservable<L> {
    private static final String TAG = BaseUseCase.class.getSimpleName();

    private boolean mIsInjectorUsed;
    private WeakReference<Context> contextRef;

    public void setContextRef(@NonNull Context context) {
        this.contextRef = new WeakReference<>(context);
    }

    public Context getContextRef() {
        try {
            return contextRef.get();
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public UseCaseComponent getUseCaseComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return getApplicationComponent().newUseCaseComponent(new UseCaseModule());
    }

    private ApplicationComponent getApplicationComponent() {
        return App.getInstance().getApplicationComponent();
    }
}
