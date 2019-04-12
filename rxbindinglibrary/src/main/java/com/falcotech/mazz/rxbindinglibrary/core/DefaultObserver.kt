package com.falcotech.mazz.rxbindinglibrary.core

import io.reactivex.observers.DisposableObserver

open class DefaultObserver<T> : DisposableObserver<T>() {

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {

    }

    override fun onComplete() {

    }

}