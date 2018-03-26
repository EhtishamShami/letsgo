package com.shami.daniyalproject.api.pojo.response

/**
 * Created by Shami on 3/23/2018.
 */

data class LoginResponse(val applicationStatusCode:Int,
                         var user:User?,
                         var statusResponse:String?,
                         var applicationErrorCode:String?,
                         var errorMessage:String?
                         )

