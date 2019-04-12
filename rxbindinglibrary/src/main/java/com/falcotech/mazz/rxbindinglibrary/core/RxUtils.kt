package com.falcotech.mazz.rxbindinglibrary.core

import android.animation.Animator
import android.view.View
import android.view.animation.Animation
import com.falcotech.mazz.promiselibrary.Promise
import com.falcotech.mazz.rxbindinglibrary.observable.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.functions.HttpsCallableReference
import io.reactivex.Observable

object RxUtils {

    fun bindAnimation(animation: Animation): Observable<Any> =
        AnimationObservable(animation)
    fun bindAnimator(animator: Animator): Observable<Any> =
        AnimatorObservable(animator)
    fun bindGlobalLayout(view: View, forWidth: Boolean): Observable<Int> =
        GlobalLayoutObservable(view, forWidth)
    fun bindFirestoreGetDoc(documentReference: DocumentReference): Observable<BT_ServerResponse> =
        FirestoreGetDocObservable(documentReference)
    fun bindFirstoreUpdateDoc(documentReference: DocumentReference, data: Map<String, Any>): Observable<BT_ServerResponse> =
        FirestoreUpdateDocObservable(documentReference, data)
    fun bindFirestoreSetDoc(documentReference: DocumentReference, data: Any): Observable<BT_ServerResponse> =
        FirestoreSetDocObservable(documentReference, data)
    fun bindFunctionsCall(callableReference: HttpsCallableReference, data: HashMap<*, *>): Observable<BT_ServerResponse> =
        FunctionsCallObservable(callableReference, data)
    fun bindFirstoryQuery(query: Query): Observable<BT_ServerResponse> =
        FirestoreQueryObservable(query)
    fun <T> bindPromise(promise: Promise<T>): Observable<T> =
        PromiseObservable(promise)
}