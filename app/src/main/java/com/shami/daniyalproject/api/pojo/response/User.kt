package com.shami.daniyalproject.api.pojo.response

/**
 * Created by Shami on 3/23/2018.
 */


data class User(var id:Int?,var firstName:String?,
                var lastName:String?,
                var phoneNumber:String?,
                var gender:String?,
                var cnic:String?,
                var email:String?,
                var imageURL:String?,
                var guid:String?,
                var userType:String?
                )
{
    constructor():this(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    )
}
