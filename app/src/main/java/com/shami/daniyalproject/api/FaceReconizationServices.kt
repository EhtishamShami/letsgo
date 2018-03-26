package com.shami.daniyalproject.api

import com.shami.daniyalproject.api.pojo.request.LoginRequest
import com.shami.daniyalproject.api.pojo.request.ReconizeRequest
import com.shami.daniyalproject.api.pojo.request.RegisterRequest
import com.shami.daniyalproject.api.pojo.response.LoginResponse
import com.shami.daniyalproject.api.pojo.response.ReconizeResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


/**
 * Created by Shami on 3/23/2018.
 */


public interface FaceReconizationServices
{


    @POST(EndPoints.LOGIN_URL)
    fun loginUser(@Body loginRequest: LoginRequest):Observable<LoginResponse>

    @POST(EndPoints.RECONIZE_URL)
    fun reconizeApi(@Body registerRequest: ReconizeRequest):Observable<ReconizeResponse>


    @Multipart
    @POST(EndPoints.RECONIZE_URL)
    fun upload(@Part("isUserRecogn")isUserRecogn:RequestBody,
               @Part("fileName")fileName:RequestBody,
               @Part file: MultipartBody.Part
    ): Observable<ReconizeResponse>


    @POST(EndPoints.REGISTER_URL)
    fun registerUrl(@Body registerRequest: RegisterRequest):Observable<LoginResponse>

}