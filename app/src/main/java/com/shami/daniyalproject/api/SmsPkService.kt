package com.shami.daniyalproject.api

import retrofit2.http.GET
import retrofit2.http.Query

public interface SmsPkService{


    @GET("/")
    fun sendSms(@Query("username")username:String,@Query("password")password:String,@Query("sender")sender:String,@Query("mobile")mobile:String,@Query("message")message:String):String


}