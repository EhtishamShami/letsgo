package com.shami.daniyalproject.fragments

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.shami.daniyalproject.R
import com.shami.daniyalproject.activities.mainactivity.MainActivity
import com.shami.daniyalproject.databinding.LayoutMapsBinding


/**
 * Created by Shami on 3/4/2018.
 */
class MapFragment:BaseFragment<LayoutMapsBinding>(), OnMapReadyCallback
{


    override fun init(view: View, savedInstanceState: Bundle?) {

        val mapFragment = (activity as MainActivity).getSupportFragmentManager()
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun setLayout(): Int {
        return R.layout.layout_maps
    }


    override fun onMapReady(p0: GoogleMap) {

    }


    companion object {

        val MapFragment="MapFragment"

        val newInstance=com.shami.daniyalproject.fragments.MapFragment()

    }


}