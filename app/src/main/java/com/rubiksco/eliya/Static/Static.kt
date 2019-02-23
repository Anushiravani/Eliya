package com.rubiksco.eliya.Static

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.view.inputmethod.InputMethodManager
import com.rubiksco.eliya.WebActivity
import android.provider.MediaStore
import android.provider.DocumentsContract
import android.content.ContentUris
import android.database.Cursor
import android.os.Environment.getExternalStorageDirectory
import android.os.Build
import android.os.Environment
import android.Manifest.permission
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import java.util.*
import java.util.concurrent.TimeUnit


object Static {

    val StiteUrl = "http://192.168.3.167:1407/"
    val grant_type="password"

    val SiteApiUrl="http://192.168.3.167:1407/clubapir/"


    //Share
    val TokenName="Token"

//"680670"

    //STATIC KEYS
    val LastGetUser="LastGetUserDBs"



    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0)
    }


    public fun diffOfDaysByDateAndTime(a: Calendar, b: Calendar ): Int {

        val duration = a.timeInMillis - b.timeInMillis
        return TimeUnit.MILLISECONDS.toDays(duration).toInt()

    }

    public fun diffOfDaysByDate( a: Calendar, b: Calendar ): Int {

        clearTimeComponent(a)
        clearTimeComponent(b)

        return diffOfDaysByDateAndTime(a, b)
    }

    public fun clearTimeComponent( date: Calendar ) {

        date.set(Calendar.HOUR_OF_DAY, 0)
        date.set(Calendar.MINUTE, 0)
        date.set(Calendar.SECOND, 0)
        date.set(Calendar.MILLISECOND, 0)
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