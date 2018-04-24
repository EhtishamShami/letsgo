package com.shami.daniyalproject.api.pojo.response

/**
 * Created by Shami on 3/23/2018.
 */

data class ReconizeResponse(val applicationStatusCode:Int,
                            val imageURL:String?,
                            val statusResponse:String?,
                            val applicationErrorCode:String?,
                            val errorMessage:String?,
                            val faceId:String,
                            val user:User
                            )

