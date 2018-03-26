package com.shami.daniyalproject.fragments

import android.os.Bundle
import android.view.View
import com.shami.daniyalproject.R
import com.shami.daniyalproject.databinding.LayoutMapsBinding

/**
 * Created by Shami on 3/4/2018.
 */
class MapFragment:BaseFragment<LayoutMapsBinding>()
{
    override fun init(view: View, savedInstanceState: Bundle?) {
    }

    override fun setLayout(): Int {
        return R.layout.layout_maps
    }


    companion object {

        val MapFragment="MapFragment"

        val newInstance=com.shami.daniyalproject.fragments.MapFragment()

    }


}