package com.shami.daniyalproject.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity



/**
 * Created by Shami on 3/10/2018.
 */

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int,fragmentTag:String,isAddToBackStack:Boolean) {
    supportFragmentManager.transact {
        if(isAddToBackStack)
        { addToBackStack(null) }
        replace(frameId, fragment,fragmentTag)
    }
}

fun AppCompatActivity.changeFragment(fragment: Fragment, frameId: Int, checkForBackstack: Boolean) {

    if (checkForBackstack) {
        val check = supportFragmentManager.backStackEntryCount
        val nameOfFragment = fragment.javaClass.name
        if (check > 2) {
            closeChildFragments()
            supportFragmentManager.beginTransaction().replace(frameId, fragment, nameOfFragment).addToBackStack(nameOfFragment).commit()
        } else if (check == 2) {
            closeFragment()
            supportFragmentManager.beginTransaction().replace(frameId, fragment, nameOfFragment).addToBackStack(nameOfFragment).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(frameId, fragment, nameOfFragment).addToBackStack(nameOfFragment).commit()
        }
    } else {
        closeFragment()
    }
}

fun AppCompatActivity.closeFragment() {

    val check = supportFragmentManager.backStackEntryCount

    if (check > 0) {
        val entry = supportFragmentManager.getBackStackEntryAt(check - 1) as FragmentManager.BackStackEntry
        val fragmentAtIndex = entry.getName()
        supportFragmentManager.popBackStack(fragmentAtIndex, check - 1)

    }
}

fun AppCompatActivity.closeChildFragments() {

    var check = supportFragmentManager.backStackEntryCount
    if (check > 0) {
        val i = check
        while (check > 1) {
            val entry = supportFragmentManager.getBackStackEntryAt(check - 1) as FragmentManager.BackStackEntry
            val fragmentAtIndex = entry.getName()
            supportFragmentManager.popBackStack(fragmentAtIndex, check - 1)
            check--
        }
    }
}



/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
    supportFragmentManager.transact {

        if(!fragment.isAdded)
        { add(fragment, tag) }
    }
}


/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

