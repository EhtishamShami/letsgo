package com.shami.daniyalproject.datamodels

import com.google.firebase.database.DatabaseReference
import com.shami.daniyalproject.api.pojo.response.User

/**
 * Created by Shami on 4/29/2018.
 */

data class UserDataList(val key:String,val dbRef:DatabaseReference,val user: User)