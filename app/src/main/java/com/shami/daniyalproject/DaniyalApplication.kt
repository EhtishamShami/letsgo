package com.shami.daniyalproject

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import com.shami.daniyalproject.di.ApiModule
import com.shami.daniyalproject.di.AppComponent
import com.shami.daniyalproject.di.AppModule
import com.shami.daniyalproject.di.DaggerAppComponent

/**
 * Created by Shami on 3/23/2018.
 */

class DaniyalApplication:Application()
{


    val appComponent: AppComponent by lazy{
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        FirebaseMessaging.getInstance().subscribeToTopic("all");

    }

    fun getComponent():AppComponent
    {
        return appComponent
    }


}