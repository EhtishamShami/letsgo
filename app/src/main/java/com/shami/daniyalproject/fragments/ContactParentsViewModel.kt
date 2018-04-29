package com.shami.daniyalproject.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.datamodels.UserFirebaseDataModel
import com.shami.daniyalproject.utils.Constant

/**
 * Created by Ehtisham shami on 3/27/2018.
 */


class ContactParentsViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var mFirebaseDatabase: FirebaseDatabase

    private lateinit var mDaniyalDatabaseReference: DatabaseReference

    private lateinit var mChildeEventListener: ChildEventListener

    val user= MutableLiveData<User>()

    init {

        mFirebaseDatabase= FirebaseDatabase.getInstance()

        mDaniyalDatabaseReference=mFirebaseDatabase.reference.child("user")

        mChildeEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot?, s: String?) {

                val mUser = dataSnapshot?.getValue<UserFirebaseDataModel>(UserFirebaseDataModel::class.java)
                mUser?.user?.let {
                    if(mUser.driverId== Constant.currentUser.id) {
                        user.value=it
                    }
                }


                }

            override fun onChildChanged(dataSnapshot: DataSnapshot?, s: String?) {
                val mUser = dataSnapshot?.getValue<UserFirebaseDataModel>(UserFirebaseDataModel::class.java)
                mUser?.user?.let {
                    if(mUser.driverId== Constant.currentUser.id) {
                        user.postValue(it)
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

        mDaniyalDatabaseReference.addChildEventListener(mChildeEventListener)


    }


    fun getUserList(): LiveData<User> {
        return user
    }




}