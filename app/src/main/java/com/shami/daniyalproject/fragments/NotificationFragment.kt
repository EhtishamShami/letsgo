package com.shami.daniyalproject.fragments

import android.os.Bundle
import android.view.View
import com.shami.daniyalproject.R
import com.shami.daniyalproject.databinding.LayoutNotificationBinding

/**
 * Created by Ehitshamshami on 3/26/2018.
 */

class NotificationFragment:BaseFragment<LayoutNotificationBinding>()
{

    override fun init(view: View, savedInstanceState: Bundle?) {

    }


    override fun setLayout(): Int {

        return R.layout.layout_notification
    }


    companion object {

        val NotificationFragment="NotificationFragment"

        val newInstance= NotificationFragment()


    }


}