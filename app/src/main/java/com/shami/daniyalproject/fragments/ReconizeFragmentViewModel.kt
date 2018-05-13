package com.shami.daniyalproject.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shami.daniyalproject.DaniyalApplication
import com.shami.daniyalproject.api.FaceReconizationServices
import com.shami.daniyalproject.api.SmsPkService
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.datamodels.NotificationDataModel
import com.shami.daniyalproject.utils.Constant
import com.shami.daniyalproject.utils.applySchedulersKotlin
import io.reactivex.disposables.Disposable
import okhttp3.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * Created by Ehitshamshami on 3/26/2018.
 */

class ReconizeFragmentViewModel(application: Application): AndroidViewModel(application)
{

    lateinit var disposable: Disposable

    @Inject
    lateinit var  mFaceReconizationService: FaceReconizationServices

    @Inject
    lateinit var mSendSmsService: SmsPkService


    private  var mFirebaseDatabase: FirebaseDatabase
    private  var mDaniyalDatabaseReference: DatabaseReference

    var isLoading= MutableLiveData<Boolean>()

    var user=MutableLiveData<User>()


    init {

        (application as DaniyalApplication).getComponent().inject(this)

        mFirebaseDatabase= FirebaseDatabase.getInstance()
        mDaniyalDatabaseReference=mFirebaseDatabase.reference.child("notification")
    }


    fun getUser(): LiveData<User> {
        return user
    }


    fun isLoading():LiveData<Boolean> {
        return isLoading
    }

    fun uploadImage( file: File) {
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)

        val multipartBody = MultipartBody.Part.createFormData("file", file.name,requestFile )

        val reqFile = RequestBody.create(MediaType.parse("text/plain"), file.name)

        val isUserRecogn = RequestBody.create(MediaType.parse("text/plain"), "true")


        isLoading.postValue(true)


        disposable= mFaceReconizationService.upload(isUserRecogn,reqFile,multipartBody)
                .compose(applySchedulersKotlin())
                .subscribe(
                        { result ->

                            isLoading.postValue(false)
                            if(result.applicationStatusCode==0)
                            {
                                result.user?.let {
                                    user.postValue(it)
                                    sendNotification(it.id.toString(),it.firstName)
                                    val currentTime = Calendar.getInstance().time
                                    val formatter = SimpleDateFormat("HH:mm:ss:SSS")
                                    val dateFormatted = formatter.format(currentTime)
                                    mDaniyalDatabaseReference.push().setValue(NotificationDataModel(it.id,it.firstName+" Picked UP",dateFormatted,currentTime.date.toString()+","+currentTime.month.toString()+","+currentTime.year.toString()))
                                    sendSms(it.phoneNumber,it.firstName)
                                }

                            }

                        },
                        { error ->

                            isLoading.postValue(false)
                        }
                )


    }


    fun sendSms(number:String,fristName:String) {

        try {
//            disposable=  mSendSmsService.sendSms("923325603050","2387","Masking",number,fristName+" has been picked up")
//                    .compose(applySchedulersKotlin())
//                    .subscribe()

            GetUrlContentTask().execute(number,fristName)

        }catch (ex:Exception) {

        }

    }


    private inner class GetUrlContentTask : AsyncTask<String, Int, String>() {
        override fun doInBackground(vararg urls: String): String {
            val url = URL("http://sendpk.com/api/sms.php?username=923335412463&password=8217&sender=Masking &mobile=+"+urls[0]+"&message=Your Kid "+urls[1]+" has been picked up")

            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestMethod("GET")
            connection.setDoOutput(true)
            connection.setConnectTimeout(5000)
            connection.setReadTimeout(5000)
            connection.connect()
            val rd = BufferedReader(InputStreamReader(connection.getInputStream()))
            var content = ""
            var line: String
            return content
        }

        override fun onPostExecute(result: String) {
            // this is executed on the main thread after the process is over
            // update your UI here
        }
    }




   fun sendNotification(userID:String,fristName:String){
    val JSON= MediaType.parse("application/json; charset=utf-8");

       object : AsyncTask<Void, Void, Void>() {
           override fun doInBackground(vararg params: Void): Void? {
               try {
                   val client = OkHttpClient()
                   val json = JSONObject()
                   val dataJson = JSONObject()
                   dataJson.put("body", "Hi your Kid "+fristName+" has been picked")
                   dataJson.put("title", userID)
                   json.put("notification", dataJson)
                   json.put("to","/topics/all")
                   val body = RequestBody.create(JSON, json.toString())
                   val request = Request.Builder()
                           .addHeader("Content-Type","application/json")
                           .addHeader("Authorization", "key=" + Constant.FIREBASE_LEGACY_SERVER_KEY)
                           .url("https://fcm.googleapis.com/fcm/send")
                           .post(body)
                           .build()
                   val response = client.newCall(request).execute()
                   val finalResponse = response.body()?.string()
               } catch (e: Exception) {
                   Log.d("Tag",e.toString())
                   //Log.d(TAG,e+"");
               }
               return null
           }
       }.execute()
    }


}