package com.falcotech.mazz.rxbindinglibrary

import android.view.animation.Animation
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class AnimationObservable(private val animation: Animation) : Observable<Any>(){
    override fun subscribeActual(observer: Observer<in Any>) {
        val listener = Listener(animation, observer)
        observer.onSubscribe(listener)
        animation.setAnimationListener(listener)
    }

    internal class Listener(private val animation: Animation, private val observer: Observer<in Any>) : MainThreadDisposable(), Animation.AnimationListener{
        override fun onDispose() {
            animation.setAnimationListener(null)
        }

        override fun onAnimationEnd(p0: Animation?) {
            if(!isDisposed){
                observer.onNext(AnimationLifecycle.END)
                observer.onComplete()
                dispose()
            }
        }

        override fun onAnimationRepeat(p0: Animation?) {
            if(!isDisposed){
                observer.onNext(AnimationLifecycle.REPEAT)
            }
        }

        override fun onAnimationStart(p0: Animation?) {
            if(!isDisposed){
                observer.onNext(AnimationLifecycle.START)
            }
        }

    }

}