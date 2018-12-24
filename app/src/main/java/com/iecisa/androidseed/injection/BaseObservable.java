package com.iecisa.androidseed.injection;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class for observable entities in the application
 * @param <L> the class of the listeners
 */
public abstract class BaseObservable<L> {

    // thread-safe set of listeners
    private Set<L> mListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<L, Boolean>(1));


    public final void registerListener(L listener) {
        mListeners.add(listener);
    }

    public final void unregisterListener(L listener) {
        mListeners.remove(listener);
    }

    /**
     * Get a reference to the unmodifiable set containing all the registered listeners.
     */
    protected final Set<L> getListeners() {
        return Collections.unmodifiableSet(mListeners);
    }

}
