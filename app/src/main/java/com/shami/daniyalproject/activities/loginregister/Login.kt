package com.shami.daniyalproject.activities.loginregister

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import com.shami.daniyalproject.R
import com.shami.daniyalproject.activities.BaseActivity
import com.shami.daniyalproject.activities.mainactivity.MainActivity
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.databinding.LayoutLoginBinding
import com.shami.daniyalproject.utils.Constant

/**
 * Created by Shami on 3/3/2018.
 */

class Login :BaseActivity<LayoutLoginBinding>(),LoginClickListeners {




    private lateinit var mLoginOrRegisterViewModel:LoginViewModel



    override fun init(savedInstanceState: Bundle?) {

        mLoginOrRegisterViewModel= ViewModelProviders.of(this@Login)[LoginViewModel::class.java]

        viewDataBinding?.apply {

            viewmodel=mLoginOrRegisterViewModel
            listeners=this@Login

        }

        subscribe()



    }

    override fun setLayout(): Int {
        return R.layout.layout_login
   }



    fun subscribe()
    {
        val mShowLoading=object:Observer<Boolean>
        {
            override fun onChanged(t: Boolean?) {

                t?.let {

                    if(t) {

                        showLoading()
                    }
                    else {
                        dismissProgress()
                    }

                }
            }

        }


        val userObserver=object:Observer<User>
        {
            override fun onChanged(t: User?) {
            Toast.makeText(this@Login,"User Login Sucessfully",Toast.LENGTH_SHORT).show()

                t?.let {
                    Constant.currentUser=t
                    val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                    preferences.edit().putInt(Constant.UserID, it.id).apply()
                }
                val intent=Intent(this@Login, MainActivity::class.java)
                startActivity(intent)

            }

        }


        val errorObserver=object:Observer<Throwable>{
            override fun onChanged(t: Throwable?) {

                t?.let {
                    showHttpError(it)
                }

            }

        }


        mLoginOrRegisterViewModel.getError().observe(this,errorObserver)
        mLoginOrRegisterViewModel.getLoading().observe(this,mShowLoading)
        mLoginOrRegisterViewModel.getUser().observe(this,userObserver)



    }


    /*
     Click listeners
     */


    override fun signIn(view: View) {

       mLoginOrRegisterViewModel.loginUser()

    }

    override fun newUser(view: View) {

        val intent= Intent(this,Register::class.java)
        startActivity(intent)

    }

}