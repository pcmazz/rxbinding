package com.falcotech.mazz.rxbindinglibrary.observable

import android.view.View
import android.view.ViewTreeObserver
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class GlobalLayoutObservable(private val view: View, private val forWidth: Boolean): Observable<Int>() {

    override fun subscribeActual(observer: Observer<in Int>) {
        val listener =
            Listener(view, forWidth, observer)
        observer.onSubscribe(listener)
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    internal class Listener(private val view: View, private val forWidth: Boolean, private val observer: Observer<in Int>): MainThreadDisposable(), ViewTreeObserver.OnGlobalLayoutListener{
        override fun onDispose() {
            view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }

        override fun onGlobalLayout() {
            if(!isDisposed){
                if(forWidth){
                    val width = view.measuredWidth
                    if(width > 0){
                        observer.onNext(width)
                        observer.onComplete()
                        dispose()
                    }
                }else{
                    val height = view.measuredHeight
                    if(height > 0){
                        observer.onNext(height)
                        observer.onComplete()
                        dispose()
                    }
                }
            }
        }
    }
}