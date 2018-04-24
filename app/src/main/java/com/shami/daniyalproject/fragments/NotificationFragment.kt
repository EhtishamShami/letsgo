package com.shami.daniyalproject.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.shami.daniyalproject.R
import com.shami.daniyalproject.adapters.NotificationAdapter
import com.shami.daniyalproject.databinding.LayoutNotificationBinding
import com.shami.daniyalproject.datamodels.NotificationDataModel

/**
 * Created by Ehitshamshami on 3/26/2018.
 */

class NotificationFragment:BaseFragment<LayoutNotificationBinding>()
{
    lateinit var mAdapter: NotificationAdapter
    lateinit var mNotificationViewModel: NotificationViewModel

    override fun init(view: View, savedInstanceState: Bundle?) {

        mNotificationViewModel= ViewModelProviders.of(this@NotificationFragment)[NotificationViewModel::class.java]
        setRecycler()
        subscribe()

    }

    fun subscribe() {
        val userAdded=object: Observer<NotificationDataModel> {
            override fun onChanged(t: NotificationDataModel?) {
                t?.let {
                    mAdapter.addItem(it)
                }
            }
        }

        mNotificationViewModel.getUserList().observe(this@NotificationFragment,userAdded)


    }


    fun setRecycler() {

        mAdapter= NotificationAdapter(ArrayList<NotificationDataModel>())
        viewDataBinding.parentList.layoutManager= LinearLayoutManager(context)
        viewDataBinding.parentList.adapter=mAdapter


    }

    override fun setLayout(): Int {

        return R.layout.layout_notification
    }


    companion object {

        val NotificationFragment="NotificationFragment"

        val newInstance= NotificationFragment()


    }


}