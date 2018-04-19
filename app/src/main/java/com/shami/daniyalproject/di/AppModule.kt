package com.shami.daniyalproject.di

import android.app.Application
import android.content.Context
import com.shami.daniyalproject.api.FaceReconizationServices
import com.shami.daniyalproject.api.SmsPkService
import com.shami.daniyalproject.utils.Constant
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Shami on 3/23/2018.
 */
@Module
class AppModule(val application: Application)
{


    @Singleton
    @Provides
    fun getContext():Context {
        return application;
    }



    @Singleton
    @Provides
    fun getReconizeService(retrofit: Retrofit):FaceReconizationServices {
        return retrofit.create(FaceReconizationServices::class.java)
    }


    @Singleton
    @Provides
    fun getSmsPkService(@Named(Constant.SENDPKAPI) retrofit: Retrofit): SmsPkService {

        return retrofit.create(SmsPkService::class.java)
    }



}