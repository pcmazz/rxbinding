package com.falcotech.mazz.rxbindinglibrary

import io.reactivex.observers.DisposableObserver

open class DefaultObserver<T> : DisposableObserver<T>() {

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {

    }

    override fun onComplete() {

    }

}