package com.shami.daniyalproject.datamodels

import com.shami.daniyalproject.api.pojo.response.User

/**
 * Created by Ehitshamshami on 3/27/2018.
 */

data class DriverFirebaseModel(var lat:String,var lng:String,var user: User?){

    constructor():this("0","0",null)
}