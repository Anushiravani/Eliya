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
fun Context.GetRetrofit(): Retrofit {


        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(Static.StiteUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()



        return retrofit

    }

fun Context.preference(key: String?, defaultValue: String = ""):String {

      val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

         return sharedPreferences.getString(key, defaultValue)

}

fun Context.preference(key: String,value:String, defaultValue: String = ""): PreferencesDelegate {

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    sharedPreferences.edit()
        .putString(key, value)
        .apply()
}

inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    when(T::class) {
        Boolean::class -> return this.getBoolean(key, defaultValue as Boolean) as T
        Float::class -> return this.getFloat(key, defaultValue as Float) as T
        Int::class -> return this.getInt(key, defaultValue as Int) as T
        Long::class -> return this.getLong(key, defaultValue as Long) as T
        String::class -> return this.getString(key, defaultValue as String) as T
        else -> {
            if (defaultValue is Set<*>) {
                return this.getStringSet(key, defaultValue as Set<String>) as T
            }
        }
    }

    return defaultValue
}

inline fun <reified T> SharedPreferences.put(key: String, value: T): T {
    val editor = this.edit()

    when(T::class) {
        Boolean::class -> editor.putBoolean(key, value as Boolean)
        Float::class -> editor.putFloat(key, value as Float)
        Int::class -> editor.putInt(key, value as Int)
        Long::class -> editor.putLong(key, value as Long)
        String::class -> editor.putString(key, value as String)
        else -> {
            if (value is Set<*>) {
                editor.putStringSet(key, value as Set<String>)
            }
        }
    }

    editor.apply()
    return value;
}




class PreferencesDelegate(val context: Context, val key: String, val defaultValue: String = "") {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return sharedPreferences.getString(key, defaultValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }
}