package com.shami.daniyalproject.fragments

import android.os.Bundle
import android.view.View
import com.shami.daniyalproject.R
import com.shami.daniyalproject.databinding.LayoutReconizeBinding

/**
 * Created by Shami on 3/4/2018.
 */

class ReconizeFragment:BaseFragment<LayoutReconizeBinding>()
{
    override fun init(view: View, savedInstanceState: Bundle?) {

    }

    override fun setLayout(): Int {
        return R.layout.layout_reconize
    }


    companion object {

        val ReconizeFragment="ReconizeFragment"
        val newInstance=com.shami.daniyalproject.fragments.ReconizeFragment()


    }


}