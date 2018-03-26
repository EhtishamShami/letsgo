package com.shami.daniyalproject.utils

import com.shami.daniyalproject.R
import com.shami.daniyalproject.datamodels.FragmentDataModel
import com.shami.daniyalproject.fragments.DriverProfileFragment
import com.shami.daniyalproject.fragments.MapFragment
import com.shami.daniyalproject.fragments.ReconizeFragment
import com.shami.daniyalproject.fragments.StatusFragment

/**
 * Created by Shami on 3/4/2018.
 */

object menutil
{


    /*
       Method Return the Dirver profile
     */


    fun returnDriverMenu():ArrayList<FragmentDataModel>
    {
        val driverMenuList=ArrayList<FragmentDataModel>()

        driverMenuList.add(FragmentDataModel("Mark Attendence", R.drawable.mark_attendance_icon,ReconizeFragment.newInstance))

        driverMenuList.add(FragmentDataModel("Register Student", R.drawable.register_student,ReconizeFragment.newInstance))


        driverMenuList.add(FragmentDataModel("Current Status", R.drawable.scurrent_status,ReconizeFragment.newInstance))


        driverMenuList.add(FragmentDataModel("Contact Parent", R.drawable.markattendance,ReconizeFragment.newInstance))

        return driverMenuList


    }


    /*
     Generic Utils
     */


    fun returnGenericMenu():ArrayList<FragmentDataModel>
    {

        val studentMenuList=ArrayList<FragmentDataModel>()

        studentMenuList.add(FragmentDataModel("Location",R.drawable.checklocation,MapFragment.newInstance))
        studentMenuList.add(FragmentDataModel("Mark Attendence",R.drawable.mark_attendance_icon,ReconizeFragment.newInstance))
        studentMenuList.add(FragmentDataModel("Driver Details",R.drawable.checkdriver,DriverProfileFragment.newInstance))
        studentMenuList.add(FragmentDataModel("Current Status",R.drawable.scurrent_status,StatusFragment.newInstance))

        return studentMenuList

    }




    /*
     Method return the student dashboard
     */

    fun returnRidermenu():ArrayList<FragmentDataModel>
    {
        val studentMenuList=ArrayList<FragmentDataModel>()

        studentMenuList.add(FragmentDataModel("Location",R.drawable.checklocation,MapFragment.newInstance))
        studentMenuList.add(FragmentDataModel("Notification",R.drawable.checknotification,MapFragment.newInstance))
        studentMenuList.add(FragmentDataModel("Driver Details",R.drawable.checkdriver,DriverProfileFragment.newInstance))
        studentMenuList.add(FragmentDataModel("Attendence",R.drawable.checkattendance,MapFragment.newInstance))

        return studentMenuList

    }






}