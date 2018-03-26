package com.shami.daniyalproject.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.evampsaanga.stcenterprisealcarte.util.ProgressDialogeInterface
import com.shami.daniyalproject.R
import io.vrinda.kotlinpermissions.PermissionsActivity


/**
 * Created by Shami on 3/3/2018.
 */
abstract class BaseActivity<T: ViewDataBinding>:PermissionsActivity(),ProgressDialogeInterface
{

    protected var isActive=false;

    protected lateinit var viewDataBinding: T

    var userIsInteracting=false

    companion object {
        private lateinit var mProgressDialog: ProgressDialog

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding= DataBindingUtil.setContentView(this,setLayout())
        mProgressDialog = ProgressDialog(this)
        init(savedInstanceState)
    }


    override fun onPostResume() {
        super.onPostResume()
        isActive=true
    }

    override fun onPause() {
        super.onPause()
        isActive=false
    }



    override fun onDestroy() {

        System.gc()
        System.runFinalization()

        super.onDestroy()

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val v = currentFocus
        if (v is EditText) {
            val scoops = IntArray(2)
            v.getLocationOnScreen(scoops)
            val x = event.rawX + v.left - scoops[0]
            val y = event.rawY + v.top - scoops[1]

            if (event.action == MotionEvent.ACTION_UP && (x < v.left || x >= v.right || y < v.top || y > v
                    .bottom)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v
                        .windowToken, 0)
            }
        }
        return super.dispatchTouchEvent(event)
    }


    abstract fun init(savedInstanceState: Bundle?);



    @LayoutRes
    abstract fun setLayout():Int




    override fun showLoading() {

        if(!mProgressDialog.isShowing)
        {
            showProgress(R.string.loading, null)

        }

    }



    override fun hideLoading() { dismissProgress() }
    override fun loadError(e: Throwable) { showHttpError(e) }

    /*Load error Mazhar bhi kay sath decide kartey han*/

    override fun loadError(msg: String) {}



    fun showProgress(msgResId: Int, keyListener: DialogInterface.OnKeyListener?) {
        if (isFinishing) return
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setIndeterminate(true)
        mProgressDialog.setCancelable(false)

        if (mProgressDialog.isShowing()) { return }

        if (msgResId != 0) { mProgressDialog.setMessage(resources.getString(msgResId)) }

        if (keyListener != null) {mProgressDialog.setOnKeyListener(keyListener)
        } else { mProgressDialog.setCancelable(false) }
        mProgressDialog.show()
    }


    fun dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }



    /**
     * show progress dialog.
     *
     * @param msgResId
     */
    fun showProgress(msgResId: Int) {
        showProgress(msgResId, null)
    }


    /*
     I will set it up I swear to Shakeel
     */

    protected fun showHttpError(e: Throwable) {

    }



}