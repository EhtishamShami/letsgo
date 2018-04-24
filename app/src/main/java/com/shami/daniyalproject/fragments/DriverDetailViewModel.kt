package com.shami.daniyalproject.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DriverDetailViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var mFirebaseDatabase: FirebaseDatabase

    private lateinit var mDaniyalDatabaseReference: DatabaseReference

    private lateinit var mChildeEventListener: ChildEventListener

//    val user= ObservableField<User>()

//    val userLiveData= MutableLiveData<User>()


    init {
//
//        mFirebaseDatabase= FirebaseDatabase.getInstance()
//
//        mDaniyalDatabaseReference=mFirebaseDatabase.reference.child("driver")
//
//        mChildeEventListener = object : ChildEventListener {
//            override fun onChildAdded(dataSnapshot: DataSnapshot?, s: String?) {
//
//                val mUser = dataSnapshot?.getValue<DriverFirebaseModel>(DriverFirebaseModel::class.java)
//                mUser?.user?.let {
//                  user.set(it)
//                    userLiveData.postValue(it)
//                }
//
//
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot?, s: String?) {
//                val mUser = dataSnapshot?.getValue<DriverFirebaseModel>(DriverFirebaseModel::class.java)
//                mUser?.user?.let {
//                    user.set(it)
//                    userLiveData.postValue(it)
//                }
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
//
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot?, s: String?) {
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError?) {
//
//            }
//        }
//
//        mDaniyalDatabaseReference.addChildEventListener(mChildeEventListener)


    }




//    fun getUserData():LiveData<User>
//    {
//        return userLiveData
//    }



}