package com.falcotech.mazz.rxbindinglibrary.observable

import android.animation.Animator
import com.falcotech.mazz.rxbindinglibrary.lifecycle.AnimatorLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class AnimatorObservable(private val animator: Animator) : Observable<Any>(){
    override fun subscribeActual(observer: Observer<in Any>) {
        val listener = Listener(animator, observer)
        observer.onSubscribe(listener)
        animator.addListener(listener)
    }

    internal class Listener(private val animator: Animator, private val observer: Observer<in Any>) : MainThreadDisposable(), Animator.AnimatorListener{
        override fun onDispose() {
            animator.removeAllListeners()
        }

        override fun onAnimationEnd(p0: Animator?) {
            if(!isDisposed){
                observer.onNext(AnimatorLifecycle.END)
                observer.onComplete()
                dispose()
            }
        }

        override fun onAnimationRepeat(p0: Animator?) {
            if(!isDisposed){
                observer.onNext(AnimatorLifecycle.REPEAT)
            }
        }

        override fun onAnimationCancel(p0: Animator?) {
            if(!isDisposed){
                observer.onNext(AnimatorLifecycle.CANCEL)
            }
        }

        override fun onAnimationStart(p0: Animator?) {
            if(!isDisposed){
                observer.onNext(AnimatorLifecycle.START)
            }
        }
    }
}