package com.shami.daniyalproject.fragments

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.shami.daniyalproject.R
import com.shami.daniyalproject.databinding.LayoutMapsBinding
import com.shami.daniyalproject.datamodels.DriverFirebaseModel


/**
 * Created by Shami on 3/4/2018.
 */
class MapFragment:BaseFragment<LayoutMapsBinding>(), OnMapReadyCallback
{


    lateinit var map: GoogleMap
    var isMapReady=false

    private lateinit var mapView: MapView
    // Firebase instance variables
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mChildEventListener: ChildEventListener



    override fun init(view: View, savedInstanceState: Bundle?) {


        mapView = view.findViewById(R.id.map) as MapView
        mapView.onCreate(savedInstanceState)

        // Set the map ready callback to receive the GoogleMap object
        mapView.getMapAsync(this)



        mFirebaseDatabase= FirebaseDatabase.getInstance()

        mDatabaseReference=mFirebaseDatabase.reference.child("driver")

        mChildEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot?, s: String?) {
                if (isMapReady) {

                    val mDriver = dataSnapshot?.getValue<DriverFirebaseModel>(DriverFirebaseModel::class.java)
                    mDriver?.user?.let {
                        Add_Marker(it.firstName,mDriver.lat,mDriver.lng)
                    }
                }


            }

            override fun onChildChanged(dataSnapshot: DataSnapshot?, s: String?) {

                if(isMapReady)
                {

                    val mDriver = dataSnapshot?.getValue<DriverFirebaseModel>(DriverFirebaseModel::class.java)
                    mDriver?.user?.let {
                        Add_Marker(it.firstName,mDriver.lat,mDriver.lng)
                    }

                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot?, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError?) {

            }
        }

        mDatabaseReference.addChildEventListener(mChildEventListener)


    }



    override fun setLayout(): Int {
        return R.layout.layout_maps
    }


    override fun onMapReady(p0: GoogleMap) {
        map=p0
        isMapReady=true
        mapView.onResume();

    }

    fun Add_Marker(DriverName: String, DriverLat: String, DriverLong: String) {
        try {
            val lat = java.lang.Double.parseDouble(DriverLat)
            val longi = java.lang.Double.parseDouble(DriverLong)

            val sydney = LatLng(lat, longi)
            map.clear()
            map.addMarker(MarkerOptions().position(sydney)
                    .title(DriverName))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }


        companion object {

        val MapFragment="MapFragment"

        val newInstance=com.shami.daniyalproject.fragments.MapFragment()

    }


}