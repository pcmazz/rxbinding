package com.falcotech.mazz.rxbindinglibrary.observable

import com.falcotech.mazz.rxbindinglibrary.core.BT_ServerResponse
import com.falcotech.mazz.rxbindinglibrary.core.BackgroundDisposable
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Observable
import io.reactivex.Observer

class FirestoreGetDocObservable(private val docRef: DocumentReference) : Observable<BT_ServerResponse>(){
    override fun subscribeActual(observer: Observer<in BT_ServerResponse>) {
        val listener = Listener(observer)
        observer.onSubscribe(listener)
        docRef.get().addOnCompleteListener(listener)
    }

    internal class Listener(private val observer: Observer<in BT_ServerResponse>): BackgroundDisposable(),
        OnCompleteListener<DocumentSnapshot?> {

        override fun onComplete(task: Task<DocumentSnapshot?>) {
            if(task.isSuccessful){
                if(task.result != null && task.result?.data != null){
                    observer.onNext(
                        BT_ServerResponse(
                            "Success",
                            task.result!!
                        )
                    )
                }else{
                    observer.onNext(
                        BT_ServerResponse(
                            "Failure",
                            null,
                            false
                        )
                    )
                }
                observer.onComplete()
                dispose()
            }else{
                observer.onNext(
                    BT_ServerResponse(
                        "Failure",
                        task.exception,
                        false
                    )
                )
                observer.onComplete()
                dispose()
            }
        }

        override fun onDispose() {

        }
    }
}