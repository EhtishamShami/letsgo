package com.shami.daniyalproject.activities.loginregister

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shami.daniyalproject.DaniyalApplication
import com.shami.daniyalproject.api.FaceReconizationServices
import com.shami.daniyalproject.api.pojo.request.RegisterRequest
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.datamodels.DriverFirebaseModel
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
 * Created by Shami on 3/4/2018.
 */


interface RegisterClickListeners
{

    fun register(view: View)
    fun captureImage(view: View)


}



class RegisterViewModel(app:Application): AndroidViewModel(app)
{

    var disposable: Disposable?=null

    @Inject
    lateinit var  mFaceReconizationService: FaceReconizationServices


    private  var mFirebaseDatabase: FirebaseDatabase
    private  var mDaniyalDatabaseReference: DatabaseReference


    var firstName=ObservableField<String>()
    var lastName=ObservableField<String>()
    var email=ObservableField<String>()
    var phoneNumber=ObservableField<String>()
    var password=ObservableField<String>()
    var idCard=ObservableField<String>()

    var isLoading=MutableLiveData<Boolean>()


    var user=MutableLiveData<User>()

    init {

        (app as DaniyalApplication).getComponent().inject(this)
        setValues()

       mFirebaseDatabase= FirebaseDatabase.getInstance()
       mDaniyalDatabaseReference=mFirebaseDatabase.reference.child("driver")



    }


    fun registerUser(imageUrl:String) {
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
                                                        "driver",
                                                        "anythingBro"
                                                        ))
                .compose(applySchedulersKotlin())
                .subscribe(
                        { result ->

                            isLoading.postValue(false)
                            if(result.applicationStatusCode==0)
                            {
                                result.user?.let {
                                    mDaniyalDatabaseReference.push().setValue(DriverFirebaseModel("0","0",it))

                                    user.postValue(result.user)
                                }
                            }

                        },
                        { error ->

                            isLoading.postValue(false)
                        }
                )



    }

    override fun onCleared() {
        super.onCleared()


        disposable?.let {
            it.dispose()
        }


    }

    fun setValues() {

        firstName.set("James")
        lastName.set("James")
        email.set("James")
        phoneNumber.set("911")
        password.set("James")
        idCard.set("James")

    }


    fun isLoading():LiveData<Boolean> {
        return isLoading
    }

    fun getUser():LiveData<User> {
        return user
    }

    fun uploadImage(file: File) {

        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)

        val multipartBody = MultipartBody.Part.createFormData("file", file.name,requestFile )

        val reqFile = RequestBody.create(MediaType.parse("text/plain"), file.name)

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
                                    registerUser(result.imageURL)
                                }

                            }

                        },
                        { error ->

                            isLoading.postValue(false)
                        }
                )


    }



}