package com.shami.daniyalproject.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shami.daniyalproject.R
import com.shami.daniyalproject.api.pojo.response.User
import kotlinx.android.synthetic.main.parent_item.view.*

/**
 * Created by Ehitshamshami on 3/28/2018.
 */

class ParentAdapter(var mList:ArrayList<User>,var mClickListener:callButton) :RecyclerView.Adapter<ParentAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.parent_item,parent,false)

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


    public fun addItem(user:User) {
        mList.add(user)
        notifyItemInserted(mList.size-1)
    }



    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val parentName=itemView.nameTV
        val call_button=itemView.callBtn
        val parentEmail=itemView.useremailTV

        init {
            call_button.setOnClickListener {
                mClickListener.callParnet(mList[adapterPosition])
            }
        }

        fun setUserName(user:User) {
            parentName.text=user.firstName
            parentEmail.text=user.email
        }

    }


    interface callButton {

        fun callParnet(user: User)

    }

}