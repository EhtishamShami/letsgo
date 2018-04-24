package com.shami.daniyalproject.firebasenotification

import android.preference.PreferenceManager
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.shami.daniyalproject.utils.Constant


/**
 * Created by Shami on 4/20/2018.
 */
public class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.edit().putString(Constant.FIREBASE_TOKEN, refreshedToken).apply()
    }
}
