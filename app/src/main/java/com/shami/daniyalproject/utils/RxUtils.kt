package com.shami.daniyalproject.utils

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Shami on 3/23/2018.
 */

inline fun <T>applySchedulersKotlin(): ObservableTransformer<T, T>
{
    return object:ObservableTransformer<T,T>{
        override fun apply(upstream: Observable<T>): ObservableSource<T> {

            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

        }
    }


}
