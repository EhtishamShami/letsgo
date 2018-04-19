package com.shami.daniyalproject.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shami.daniyalproject.DaniyalApplication
import com.shami.daniyalproject.api.FaceReconizationServices
import com.shami.daniyalproject.api.SmsPkService
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.utils.applySchedulersKotlin
import io.reactivex.disposables.Disposable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
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
    lateinit var mSendSmsService:SmsPkService


    var isLoading= MutableLiveData<Boolean>()

    var user=MutableLiveData<User>()


    init {

        (application as DaniyalApplication).getComponent().inject(this)

    }


    fun getUser(): LiveData<User> {
        return user
    }


    fun isLoading():LiveData<Boolean> {
        return isLoading
    }

    fun uploadImage( file: File)
    {
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
                                }

                            }

                        },
                        { error ->

                            isLoading.postValue(false)
                        }
                )


    }


    fun sendSms(number:String,fristName:String)
    {
        mSendSmsService.sendSms("923325603050","2387","Masking",number,fristName+" has been picked up")

    }

}