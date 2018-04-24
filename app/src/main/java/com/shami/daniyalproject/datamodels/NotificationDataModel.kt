package com.shami.daniyalproject.datamodels

/**
 * Created by Shami on 4/21/2018.
 */

data class NotificationDataModel(var id:Int,var notification:String,var time:String,var date:String){

    constructor():this(0,"Your Kid has been picked Up","","")
}