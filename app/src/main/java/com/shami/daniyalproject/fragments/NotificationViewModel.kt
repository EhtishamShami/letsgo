package com.shami.daniyalproject.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.shami.daniyalproject.datamodels.NotificationDataModel
import com.shami.daniyalproject.utils.Constant

/**
 * Created by Shami on 4/21/2018.
 */
class NotificationViewModel(application: Application):AndroidViewModel(application){

    private lateinit var mFirebaseDatabase: FirebaseDatabase

    private lateinit var mDaniyalDatabaseReference: DatabaseReference

    private lateinit var mChildeEventListener: ChildEventListener

    val user= MutableLiveData<NotificationDataModel>()


    init {

        mFirebaseDatabase= FirebaseDatabase.getInstance()

        mDaniyalDatabaseReference=mFirebaseDatabase.reference.child("notification")

        mChildeEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot?, s: String?) {

                val mUser = dataSnapshot?.getValue<NotificationDataModel>(NotificationDataModel::class.java)

                mUser?.id.let {
                    if(it==Constant.currentUser.id){
                        user.value=mUser
                    }
                }


            }

            override fun onChildChanged(dataSnapshot: DataSnapshot?, s: String?) {

                val mUser = dataSnapshot?.getValue<NotificationDataModel>(NotificationDataModel::class.java)



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


    fun getUserList(): LiveData<NotificationDataModel> {
        return user
    }


}