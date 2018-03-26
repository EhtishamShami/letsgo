package com.shami.daniyalproject.fragments

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Shami on 3/3/2018.
 */
abstract class BaseFragment<T: ViewDataBinding>: Fragment()
{

    protected lateinit var viewDataBinding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view=inflater.inflate(setLayout(),container,false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding= DataBindingUtil.bind(view)

        init(view,savedInstanceState)
    }

    abstract fun init(view: View, savedInstanceState: Bundle?)
    abstract fun setLayout():Int;


}