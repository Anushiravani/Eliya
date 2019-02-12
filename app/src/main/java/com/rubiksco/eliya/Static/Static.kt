package com.rubiksco.eliya.Static

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.view.inputmethod.InputMethodManager
import com.rubiksco.eliya.WebActivity


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

    fun ShowWeb(context :Context , url :String? , title :String? , color: Int?=null){


        //google.com/¨;
          val builder = CustomTabsIntent.Builder()
          val customTabsIntent = builder.build()
        color?.let {
            builder.setToolbarColor(color)

        }
      //  customTabsIntent.launchUrl(context, Uri.parse(url));
        customTabsIntent.launchUrl(context,Uri.parse(url))
/*
        val intent = Intent(context, WebActivity::class.java)
        intent.putExtra("url",url)
        intent.putExtra("title",title)
        context.startActivity(intent)*/
    }


}