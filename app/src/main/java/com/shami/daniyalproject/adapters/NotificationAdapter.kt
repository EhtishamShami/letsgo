package com.shami.daniyalproject.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shami.daniyalproject.R
import com.shami.daniyalproject.datamodels.NotificationDataModel
import kotlinx.android.synthetic.main.notificaiton_item.view.*

/**
 * Created by Shami on 4/21/2018.
 */

class NotificationAdapter(var mList:ArrayList<NotificationDataModel>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.notificaiton_item,parent,false)

        return ViewHolder(view)

    }


    override fun getItemCount(): Int {

        return mList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(holder is RecyclerView.ViewHolder) {
            holder.setUserName(mList[position])
        }
    }


    public fun addItem(user: NotificationDataModel) {
        mList.add(user)
        notifyItemInserted(mList.size-1)
    }



    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val title=itemView.titleTV
        val time=itemView.timeTV
        val date=itemView.dateTV

        init {
        }

        fun setUserName(firebaseDataModel: NotificationDataModel) {
            title.text=firebaseDataModel.notification
            time.text=firebaseDataModel.time
            date.text=firebaseDataModel.date
        }

    }



}