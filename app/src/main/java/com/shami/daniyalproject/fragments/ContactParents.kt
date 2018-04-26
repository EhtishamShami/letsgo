package com.shami.daniyalproject.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.shami.daniyalproject.R
import com.shami.daniyalproject.activities.mainactivity.MainActivity
import com.shami.daniyalproject.adapters.ParentAdapter
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.databinding.LayoutCurrentStatusBinding
import io.vrinda.kotlinpermissions.PermissionCallBack


/**
 * Created by Shami on 3/4/2018.
 */

class ContactParents :BaseFragment<LayoutCurrentStatusBinding>()
{

    lateinit var mAdapter:ParentAdapter


    lateinit var mViewModel:ContactParentsViewModel


    override fun init(view: View, savedInstanceState: Bundle?) {



        mViewModel= ViewModelProviders.of(this@ContactParents)[ContactParentsViewModel::class.java]

        setRecycler()
        subscribe()

    }


    fun subscribe() {
        val userAdded=object:Observer<User>{
            override fun onChanged(t: User?) {
                t?.let {
                    mAdapter.addItem(it)
                }
            }
        }

        mViewModel.getUserList().observe(this@ContactParents,userAdded)


    }


    fun setRecycler() {

        mAdapter= ParentAdapter(ArrayList<User>(),object:ParentAdapter.callButton{

            override fun deleteParent(user: User) {

            }

            override fun callParnet(user: User) {

                (activity as MainActivity).requestPermissions(android.Manifest.permission.CALL_PHONE,object : PermissionCallBack {
                    override fun permissionGranted() {
                        super.permissionGranted()
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:"+user.phoneNumber)
                        startActivity(callIntent)
                    }

                    override fun permissionDenied() {
                        super.permissionDenied()
                    }
                })

            }
        })

        viewDataBinding.parentList.layoutManager= LinearLayoutManager(context)
        viewDataBinding.parentList.adapter=mAdapter


    }


    override fun setLayout(): Int {
        return R.layout.layout_current_status
    }






    companion object {

        val ContactParents="ContactParents"
        val newInstance=com.shami.daniyalproject.fragments.ContactParents()

    }



}