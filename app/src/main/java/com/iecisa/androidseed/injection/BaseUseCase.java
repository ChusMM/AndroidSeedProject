package com.iecisa.androidseed.injection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.iecisa.androidseed.App;
import com.iecisa.androidseed.injection.application.ApplicationComponent;
import com.iecisa.androidseed.injection.usecase.UseCaseModule;
import com.iecisa.androidseed.injection.usecase.UseCaseComponent;
import com.iecisa.androidseed.mvc.BaseObservable;

import java.lang.ref.WeakReference;

import retrofit2.Call;

public class BaseUseCase<L, C> extends BaseObservable<L> {
    private static final String TAG = BaseUseCase.class.getSimpleName();

    private boolean mIsInjectorUsed;
    private WeakReference<Context> contextRef;

    @Nullable
    protected Call<C> mCall;

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

    protected UseCaseComponent getUseCaseComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return getApplicationComponent().newUseCaseComponent(new UseCaseModule());
    }

    protected ApplicationComponent getApplicationComponent() {
        return App.getInstance().getApplicationComponent();
    }

    protected void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }
}