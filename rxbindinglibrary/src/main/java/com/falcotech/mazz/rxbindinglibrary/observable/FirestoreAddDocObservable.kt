package com.falcotech.mazz.rxbindinglibrary.observable

import com.falcotech.mazz.rxbindinglibrary.core.BT_ServerResponse
import com.falcotech.mazz.rxbindinglibrary.core.BackgroundDisposable
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import io.reactivex.Observable
import io.reactivex.Observer

class FirestoreAddDocObservable(private val collectionRef: CollectionReference, private val data: Any) : Observable<BT_ServerResponse>(){
    override fun subscribeActual(observer: Observer<in BT_ServerResponse>) {
        val listener = Listener(observer)
        observer.onSubscribe(listener)
        collectionRef.add(data).addOnCompleteListener(listener)
    }

    internal class Listener(private val observer: Observer<in BT_ServerResponse>): BackgroundDisposable(),
        OnCompleteListener<DocumentReference> {

        override fun onComplete(task: Task<DocumentReference>) {
            if(task.isSuccessful){
                observer.onNext(BT_ServerResponse("Success", task.result!!))
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
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}