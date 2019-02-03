package com.iecisa.androidseed.injection;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.iecisa.androidseed.mvc.BaseObservable;

import java.lang.ref.WeakReference;

public class BaseUseCase<L> extends BaseObservable<L> {
    private static final String TAG = BaseUseCase.class.getSimpleName();

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
}
