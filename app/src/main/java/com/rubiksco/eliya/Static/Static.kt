package com.rubiksco.eliya.Static

import android.app.Activity
import android.view.inputmethod.InputMethodManager


object Static {

    val StiteUrl = "https://tgmapp.ir/"
    val grant_type="password"

    val SiteApiUrl="https://tgmapp.ir/shopsapi/"


    //Share
    val TokenName="Token"



    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0)
    }


}