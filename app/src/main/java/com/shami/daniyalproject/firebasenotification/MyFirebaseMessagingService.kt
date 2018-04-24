package com.shami.daniyalproject.firebasenotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.preference.PreferenceManager
import android.support.annotation.RequiresApi
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.shami.daniyalproject.R
import com.shami.daniyalproject.activities.loginregister.Login
import com.shami.daniyalproject.utils.Constant
import java.util.*




/**
 * Created by Shami on 4/20/2018.
 */
class MyFirebaseMessagingService: FirebaseMessagingService(){


    private val ADMIN_CHANNEL_ID = "admin_channel"
    private var notificationManager: NotificationManager? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val notificationIntent = Intent(this, Login::class.java)
//        if (MainActivity.isAppRunning) {
//            //Some action
//        } else {
//            //Show notification as usual
//        }

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT)

        //You should use an actual ID instead
        val notificationId = Random().nextInt(60000)






        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels()
        }

//        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(remoteMessage.data["title"])
//                .setStyle(NotificationCompat.BigPictureStyle())
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
// then you use
       val id =prefs.getInt(Constant.UserID, 0)

        val title= remoteMessage.notification?.title




        Log.d("Tag",remoteMessage.toString())

        val noti = Notification.Builder(this)
                .setContentTitle("Picked UP")
                .setContentText("Your Kid has been picked up")
                .setSmallIcon(R.drawable.bus_logo)
                .build()
        notificationManager!!.notify(notificationId, noti)


        if(id==title?.toInt()) {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val adminChannelName = getString(R.string.notifications_admin_channel_name)
        val adminChannelDescription = getString(R.string.notifications_admin_channel_description)

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        if (notificationManager != null) {
            notificationManager!!.createNotificationChannel(adminChannel)
        }
    }
}