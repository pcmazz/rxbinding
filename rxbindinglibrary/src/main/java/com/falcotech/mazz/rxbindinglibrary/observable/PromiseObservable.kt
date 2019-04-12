package com.falcotech.mazz.rxbindinglibrary.observable

import android.util.Log
import com.falcotech.mazz.promiselibrary.Promise
import com.falcotech.mazz.rxbindinglibrary.core.BackgroundDisposable
import io.reactivex.Observable
import io.reactivex.Observer
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PromiseObservable<T>(private val promise: Promise<T>) : Observable<T>(){

    private lateinit var listener: Listener<T>
    override fun subscribeActual(observer: Observer<in T>) {
        listener = Listener(promise, observer)
        observer.onSubscribe(listener)
        listener.execute()
    }

    internal class Listener<T>(private val promise: Promise<T>, private val observer: Observer<in T>): BackgroundDisposable(), CoroutineScope{
        private val job = Job()
        override fun onDispose() {
            Log.d("DEBUG", "PromiseObservable : onDispose")
            if(job.isActive){
                job.cancel()
            }
        }

        override val coroutineContext: CoroutineContext
            get() = job

        fun execute(){
            this.launch {
                observer.onNext(promise.await())
                observer.onComplete()
                dispose()
            }
        }
    }
}