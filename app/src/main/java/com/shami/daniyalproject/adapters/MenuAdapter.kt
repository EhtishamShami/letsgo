package com.shami.daniyalproject.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shami.daniyalproject.R
import com.shami.daniyalproject.clickListeners.menuItemClickListener
import com.shami.daniyalproject.datamodels.FragmentDataModel
import kotlinx.android.synthetic.main.menu_item.view.*

/**
 * Created by Shami on 3/10/2018.
 */

class MenuAdaper(var mList:ArrayList<FragmentDataModel>,var mClickListener:menuItemClickListener):RecyclerView.Adapter<MenuAdaper.ViewHolder>()
{




    override fun getItemCount(): Int {

        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(holder is RecyclerView.ViewHolder)
        {
            holder.setMenu(mList[position])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.menu_item,parent,false)
        return ViewHolder(view)

    }


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val menuItem=itemView.menuItemIV

        init {


            menuItem.setOnClickListener {

                mClickListener.selectItem(mList[adapterPosition])
            }

        }


        fun setMenu(mCurrentFragment:FragmentDataModel)
        {
            menuItem.setImageResource(mCurrentFragment.mfragmentIcon)



        }

    }



}