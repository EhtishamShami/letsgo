package com.shami.daniyalproject.fragments

import android.os.Bundle
import android.view.View
import com.shami.daniyalproject.R
import com.shami.daniyalproject.databinding.LayoutDriverProfileBinding

/**
 * Created by Shami on 3/4/2018.
 */

class DriverProfileFragment:BaseFragment<LayoutDriverProfileBinding>()
{
    override fun init(view: View, savedInstanceState: Bundle?) {
    }

    override fun setLayout(): Int {
        return R.layout.layout_driver_profile
    }


    companion object {
        val DriverProfileFragment="DriverProfileFragment"

        val newInstance=com.shami.daniyalproject.fragments.DriverProfileFragment()

    }


}