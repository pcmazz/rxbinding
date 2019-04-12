package com.falcotech.mazz.rxbindinglibrary.core

import android.os.Looper
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean

abstract class BackgroundDisposable : Disposable {

    private val unsubscribed = AtomicBoolean(false)

    override fun isDisposed(): Boolean {
        return unsubscribed.get()
    }

    override fun dispose() {
        if(unsubscribed.compareAndSet(false, true)){
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Schedulers.newThread().scheduleDirect {
                    onDispose()
                }
            } else {
                onDispose()
            }
        }
    }

    protected abstract fun onDispose()
}