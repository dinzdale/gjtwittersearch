package com.gmjproductions.gjtwittersearch.ui

import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import com.gmjproductions.gjtwittersearch.R


/**
 * Created by garyjacobs on 1/19/18.
 */
fun FragmentActivity.fragmentLoader(fragment: Fragment, container: Int, tag: String, addToBackStack: Boolean = false) {
    val fragTransaction = this.supportFragmentManager
            .beginTransaction()
            .replace(container, fragment, tag)
    if (addToBackStack) {
        fragTransaction.addToBackStack(tag)
    }
    fragTransaction.commit()
}

fun FragmentActivity.findFragment(tag: String): Fragment? = supportFragmentManager
        .findFragmentByTag(tag)

fun FragmentActivity.removeFragment(tag: String) {
    val fragmentToRemove = findFragment(tag)
    fragmentToRemove?.let {
        supportFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
    }
}

fun Context.showTweetAlertDialog(message: Int, title: Int? = R.string.default_tweet_dialog_title, icon: Int? = com.twitter.sdk.android.tweetui.R.drawable.tw__ic_logo_blue, okCallback: (() -> Unit)? = null) {
    showTweetAlertDialog(resources.getString(message), title, icon, okCallback)
}

fun Context.showTweetAlertDialog(message: String, title: Int? = R.string.default_tweet_dialog_title, icon: Int? = com.twitter.sdk.android.tweetui.R.drawable.tw__ic_logo_blue, okCallback: (() -> Unit)? = null) {
    AlertDialog.Builder(this)
            .setMessage(message)
            .setTitle(title!!)
            .setIcon(icon!!)
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
                    okCallback?.let {
                        okCallback.invoke()
                    }
                }
            })
            .create()
            .show()

}
