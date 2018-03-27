package com.shami.daniyalproject.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shami.daniyalproject.DaniyalApplication
import com.shami.daniyalproject.api.FaceReconizationServices
import com.shami.daniyalproject.api.pojo.request.RegisterRequest
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.datamodels.UserFirebaseDataModel
import com.shami.daniyalproject.utils.Constant
import com.shami.daniyalproject.utils.applySchedulersKotlin
import io.reactivex.disposables.Disposable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

/**
 * Created by Ehtisham Shami on 3/26/2018.
 */

class RegisterViewModel(application: Application): AndroidViewModel(application)
{

    lateinit var disposable: Disposable

    @Inject
    lateinit var  mFaceReconizationService: FaceReconizationServices


    var firstName= ObservableField<String>()
    var lastName= ObservableField<String>()
    var email= ObservableField<String>()
    var phoneNumber= ObservableField<String>()
    var password= ObservableField<String>()
    var idCard= ObservableField<String>()

    var isLoading= MutableLiveData<Boolean>()


    var user= MutableLiveData<User>()

    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mDaniyalDatabaseReference: DatabaseReference




    init {

        (application as DaniyalApplication).getComponent().inject(this)
        setValues()

        mFirebaseDatabase= FirebaseDatabase.getInstance()
        mDaniyalDatabaseReference=mFirebaseDatabase.reference.child("user")


    }


    fun registerUser(imageUrl:String)
    {
        isLoading.postValue(true)
        disposable= mFaceReconizationService.registerUrl(RegisterRequest(lastName.get().toString().trim(),
                firstName.get().toString().trim(),
                password.get().toString().trim(),
                phoneNumber.get().toString().trim(),
                "Male",
                imageUrl,
                password.get().toString().trim(),
                idCard.get().toString().trim(),
                email.get().toString().trim(),
                "user"
        ))
                .compose(applySchedulersKotlin())
                .subscribe(
                        { result ->

                            isLoading.postValue(false)
                            if(result.applicationStatusCode==0)
                            {
                                result.user?.let {
                                    mDaniyalDatabaseReference.push().setValue(UserFirebaseDataModel(Constant.currentUser.id,it))
                                    user.postValue(result.user)
                                }
                            }

                        },
                        { error ->

                            isLoading.postValue(false)
                        }
                )



    }


    fun setValues()
    {

        firstName.set("Ehtisham")
        lastName.set("Ehtisham")
        email.set("Ehtisham")
        phoneNumber.set("03455650843")
        password.set("Ehtisham")
        idCard.set("Ehtisham")

    }


    fun isLoading(): LiveData<Boolean>
    {
        return isLoading
    }

    fun getUser(): LiveData<User>
    {
        return user
    }

    fun uploadImage(requestFile: RequestBody, fileName:String, file: File)
    {
        val multipartBody = MultipartBody.Part.createFormData("file", "mypic.png", requestFile)

        val reqFile = RequestBody.create(MediaType.parse("text/plain"), fileName)

        val isUserRecogn = RequestBody.create(MediaType.parse("text/plain"), "false")


        isLoading.postValue(true)


        disposable= mFaceReconizationService.upload(isUserRecogn,reqFile,multipartBody)
                .compose(applySchedulersKotlin())
                .subscribe(
                        { result ->

                            isLoading.postValue(false)
                            if(result.applicationStatusCode==0)
                            {
                                result.imageURL?.let{
                                    registerUser(it)
                                }

                            }

                        },
                        { error ->

                            isLoading.postValue(false)
                        }
                )


    }



}