package com.falcotech.mazz.rxbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.falcotech.mazz.promiselibrary.PromiseUtils
import com.falcotech.mazz.promiselibrary.then
import com.falcotech.mazz.rxbindinglibrary.core.DefaultObserver
import com.falcotech.mazz.rxbindinglibrary.observable.PromiseObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
    }

    private fun test(){
        btnTest.setOnClickListener {
            val promise = PromiseUtils.ofBg { "WALLYRUS" }
                .then { "BALLYRUS$it" }
                .then { PromiseUtils.ofUi { tvHelloWorld.text = it as String } }
            val observable = PromiseObservable(promise)
            observable
                .subscribeOn(Schedulers.newThread())
                .subscribe(object : DefaultObserver<Any?>(){
                override fun onNext(t: Any?) {
                    Log.d("DEBUG", "onNext result = $t")
                }
            })

            //observable.execute()
        }
    }
}
