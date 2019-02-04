package com.rubiksco.eliya.Static

import android.app.Activity
import android.view.inputmethod.InputMethodManager


object Static {

    val StiteUrl = "https://eliya.ir"


    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0)
    }


}