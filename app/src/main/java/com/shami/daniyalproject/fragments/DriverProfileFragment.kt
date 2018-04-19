package com.shami.daniyalproject.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.shami.daniyalproject.R
import com.shami.daniyalproject.activities.mainactivity.MainActivity
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.clickListeners.InfoViewClickListeners
import com.shami.daniyalproject.databinding.LayoutDriverProfileBinding
import io.vrinda.kotlinpermissions.PermissionCallBack

/**
 * Created by Shami on 3/4/2018.
 */

class DriverProfileFragment:BaseFragment<LayoutDriverProfileBinding>(),InfoViewClickListeners
{


    lateinit var mDriverProileViewModel:DriverDetailViewModel

    override fun init(view: View, savedInstanceState: Bundle?) {


        mDriverProileViewModel=ViewModelProviders.of(this@DriverProfileFragment)[DriverDetailViewModel::class.java]


        viewDataBinding.apply {
            viewmodel=mDriverProileViewModel
            listeners=this@DriverProfileFragment
        }

        subscribe(view)
    }

    fun subscribe(view:View) {

        var userObserver=object: Observer<User> {
            override fun onChanged(t: User?) {

                t?.let {
                    Glide.with(view)
                            .load(it.imageURL).into(viewDataBinding.driverPhotoIV)
                }

            }
        }

        mDriverProileViewModel.getUserData().observe(this@DriverProfileFragment,userObserver)

    }





    override fun setLayout(): Int {
        return R.layout.layout_driver_profile
    }

    override fun call(view: View) {
        (activity as MainActivity).requestPermissions(android.Manifest.permission.CALL_PHONE,object : PermissionCallBack {
            override fun permissionGranted() {
                super.permissionGranted()
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:"+viewDataBinding.phoneNumberET.text)
                startActivity(callIntent)
            }

            override fun permissionDenied() {
                super.permissionDenied()
            }
        })
    }

    companion object {
        val DriverProfileFragment="DriverProfileFragment"

        val newInstance=com.shami.daniyalproject.fragments.DriverProfileFragment()

    }


}