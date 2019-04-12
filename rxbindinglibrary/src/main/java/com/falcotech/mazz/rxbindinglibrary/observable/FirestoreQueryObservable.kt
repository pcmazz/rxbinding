package com.falcotech.mazz.rxbindinglibrary.observable

import com.falcotech.mazz.rxbindinglibrary.core.BT_ServerResponse
import com.falcotech.mazz.rxbindinglibrary.core.BackgroundDisposable
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Observable
import io.reactivex.Observer

class FirestoreQueryObservable(private val query: Query) : Observable<BT_ServerResponse>(){

    override fun subscribeActual(observer: Observer<in BT_ServerResponse>) {
        val listener = Listener(observer)
        observer.onSubscribe(listener)
        query.get().addOnCompleteListener(listener)
    }

    internal class Listener(private val observer: Observer<in BT_ServerResponse>): BackgroundDisposable(),
        OnCompleteListener<QuerySnapshot?> {
        override fun onComplete(task: Task<QuerySnapshot?>) {
            if(task.isSuccessful){
                if(task.result != null){
                    observer.onNext(
                        BT_ServerResponse(
                            "Success",
                            task.result!!.documents
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