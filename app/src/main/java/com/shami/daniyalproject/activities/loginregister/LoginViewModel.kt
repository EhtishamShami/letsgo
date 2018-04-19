package com.shami.daniyalproject.activities.loginregister

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.view.View
import com.shami.daniyalproject.DaniyalApplication
import com.shami.daniyalproject.api.FaceReconizationServices
import com.shami.daniyalproject.api.pojo.request.LoginRequest
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.utils.applySchedulersKotlin
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Shami on 3/3/2018.
 */


interface LoginClickListeners
{

    fun signIn(view:View)
    fun newUser(view:View)

}


class LoginViewModel(app:Application): AndroidViewModel(app)
{

    var username=ObservableField<String>()
    var password=ObservableField<String>()

    var errorMessage= MutableLiveData<Throwable>()

    lateinit var disposable: Disposable


    @Inject
    lateinit var  mFaceReconizationService:FaceReconizationServices


    var isRequestProcessing=MutableLiveData<Boolean>()

    var currentUser=MutableLiveData<User>()

    init {

        (app as DaniyalApplication).getComponent().inject(this)

    }


    fun loginUser()
    {
        isRequestProcessing.postValue(true)

        disposable=mFaceReconizationService.loginUser(LoginRequest(password.get().toString().trim(),username.get().toString().trim()))
                .compose(applySchedulersKotlin())
                .subscribe(
                        { result ->

                            isRequestProcessing.postValue(false)
                            if(result.applicationStatusCode==0)
                            {
                                currentUser.postValue(result.user)
                            }

                        },
                        { error ->
                            errorMessage.postValue(error)
                            isRequestProcessing.postValue(false)

                        }
                )


    }

    fun getError():LiveData<Throwable> {

        return errorMessage
    }


    fun getLoading():LiveData<Boolean>
    {
        return isRequestProcessing
    }

    fun getUser():LiveData<User>
    {
        return currentUser
    }



}