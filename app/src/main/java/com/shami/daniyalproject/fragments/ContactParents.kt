package com.shami.daniyalproject.fragments

import android.os.Bundle
import android.view.View
import com.shami.daniyalproject.R
import com.shami.daniyalproject.databinding.LayoutCurrentStatusBinding

/**
 * Created by Shami on 3/4/2018.
 */

class ContactParents :BaseFragment<LayoutCurrentStatusBinding>()
{
    override fun init(view: View, savedInstanceState: Bundle?) {
    }

    override fun setLayout(): Int {

        return R.layout.layout_current_status
    }


    companion object {

        val StatusFragment="ContactParents"
        val newInstance=com.shami.daniyalproject.fragments.ContactParents()

    }



}