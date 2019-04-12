package com.falcotech.mazz.rxbindinglibrary.observable

import com.falcotech.mazz.rxbindinglibrary.core.BT_ServerResponse
import com.falcotech.mazz.rxbindinglibrary.core.BackgroundDisposable
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import io.reactivex.Observable
import io.reactivex.Observer

class FirestoreUpdateDocObservable(private val docRef: DocumentReference, private val data: Map<String, Any>) : Observable<BT_ServerResponse>(){

    override fun subscribeActual(observer: Observer<in BT_ServerResponse>) {
        val listener = Listener(observer)
        observer.onSubscribe(listener)
        docRef.update(data).addOnCompleteListener(listener)
    }

    internal class Listener(private val observer: Observer<in BT_ServerResponse>): BackgroundDisposable(),
        OnCompleteListener<Void> {

        override fun onComplete(task: Task<Void>) {
            if(task.isSuccessful){
                observer.onNext(BT_ServerResponse("Success", null))
            }else{
                observer.onNext(
                    BT_ServerResponse(
                        "Failure",
                        task.exception,
                        false
                    )
                )
            }
            observer.onComplete()
            dispose()
        }

        override fun onDispose() {
        }
    }
}