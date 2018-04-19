package com.shami.daniyalproject.api.pojo.request

/**
 * Created by Shami on 3/23/2018.
 */

data class RegisterRequest(val lastName:String,
                           val firstName:String,
                           val password:String,
                           val phoneNumber:String,
                           val gender:String,
                           val imageURL:String,
                           val confirmPassword:String,
                           val cnic:String,
                           val email:String,
                           val userType:String,
                           val dirverId:String
                           )

