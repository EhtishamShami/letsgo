package com.shami.daniyalproject.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.shami.daniyalproject.R
import com.shami.daniyalproject.activities.mainactivity.MainActivity
import com.shami.daniyalproject.adapters.MenuAdaper
import com.shami.daniyalproject.clickListeners.menuItemClickListener
import com.shami.daniyalproject.databinding.LayoutDashboardBinding
import com.shami.daniyalproject.datamodels.FragmentDataModel
import com.shami.daniyalproject.utils.menutil
import com.shami.daniyalproject.utils.replaceFragmentInActivity

/**
 * Created by Shami on 3/4/2018.
 */

class DashboardFragment:BaseFragment<LayoutDashboardBinding>()
{


    lateinit var mMenuApdater:MenuAdaper


    override fun init(view: View, savedInstanceState: Bundle?) {

        mMenuApdater= MenuAdaper(menutil.returnGenericMenu(),object:menuItemClickListener{

            override fun selectItem(currentItem: FragmentDataModel) {

                (activity as MainActivity).replaceFragmentInActivity(currentItem.mFragmentInstance,R.id.dashboardItemsRecycler,true)
            }

        })

        viewDataBinding.dashboardItemsRecycler.apply {

            layoutManager=GridLayoutManager(context,2)
            adapter=mMenuApdater

        }



    }

    override fun setLayout(): Int {
        return R.layout.layout_dashboard
    }



    companion object {
        val DashboardFragment="DashboardFragment"
        val newInstance=com.shami.daniyalproject.fragments.DashboardFragment()

    }


    override fun onStop() {
        viewDataBinding.dashboardItemsRecycler.adapter=null
        super.onStop()
    }
}