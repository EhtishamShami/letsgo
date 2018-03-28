package com.shami.daniyalproject.fragments

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.shami.daniyalproject.R
import com.shami.daniyalproject.databinding.LayoutMapsBinding
import org.jetbrains.anko.doAsync


/**
 * Created by Shami on 3/4/2018.
 */
class MapFragment:BaseFragment<LayoutMapsBinding>(), OnMapReadyCallback
{


    override fun init(view: View, savedInstanceState: Bundle?) {


        doAsync {

            val mapFragment = this@MapFragment.childFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this@MapFragment)

        }


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