package com.evampsaanga.stcenterprisealcarte.util

/**
 * Created by Ehitshamshami on 1/4/2018.
 */
internal interface ProgressDialogeInterface {

    fun showLoading()

    fun hideLoading()

    fun loadError(e: Throwable)

    fun loadError(msg: String)

}
