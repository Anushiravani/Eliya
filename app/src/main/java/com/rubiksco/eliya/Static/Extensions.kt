package com.rubiksco.eliya.Static

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KProperty


fun Context.showToast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}
fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
fun Context.GetRetrofit(baseurl:String=Static.StiteUrl): Retrofit {


        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()



        return retrofit

    }

fun Context.preference(key: String?, defaultValue: String = ""):String {

      val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

         return sharedPreferences.getString(key, defaultValue)

}

fun Context.preference(key: String,value:String, defaultValue: String = "") {

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    sharedPreferences.edit()
        .putString(key, value)
        .apply();

}