package com.shami.daniyalproject.activities.mainactivity


import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shami.daniyalproject.R
import com.shami.daniyalproject.activities.BaseActivity
import com.shami.daniyalproject.databinding.ActivityMainBinding
import com.shami.daniyalproject.datamodels.DriverFirebaseModel
import com.shami.daniyalproject.utils.Constant
import io.vrinda.kotlinpermissions.PermissionCallBack



class MainActivity : BaseActivity<ActivityMainBinding>(),GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,LocationListener {


    private lateinit var mLocationRequest: LocationRequest

    private lateinit var mGoogleApiClient: GoogleApiClient
    override fun onBackPressed() {


        super.onBackPressed()
    }

    // Firebase instance variables
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference

    override fun init(savedInstanceState: Bundle?) {

        if(Constant.currentUser!=null && Constant.currentUser.userType=="driver") {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.reference.child("driver");
            buildGoogleApiClient()
        }
        if(Constant.currentUser!=null && Constant.currentUser.userType=="user"){

        }


    }

    override fun setLayout(): Int {

        return R.layout.activity_main

    }




    private fun writeNewUser( lat: String, Long: String) {
        val userData = DriverFirebaseModel( lat, Long,    Constant.currentUser);
        mDatabaseReference.child("-LA3FotuP6rHc1ouvI47").setValue(userData)

    }


    fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()

        mGoogleApiClient.connect()
    }



    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onConnected(p0: Bundle?) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(1000)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION), object : PermissionCallBack {


                override fun permissionGranted() {
                    super.permissionGranted()
                    if (ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return
                    }
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this@MainActivity);

                }

                override fun permissionDenied() {
                    super.permissionDenied()
                }
            })
        }
        else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onLocationChanged(location: Location) {

        val Latitude = location.getLatitude()
        val Longitude = location.getLongitude()
        val latLng = LatLng(location.getLatitude(), location.getLongitude())
        //map.animateCamera(cameraUpdate);
        writeNewUser(Latitude!!.toString(), Longitude!!.toString())

    }


}
