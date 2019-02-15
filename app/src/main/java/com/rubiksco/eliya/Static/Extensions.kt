package com.rubiksco.eliya.Static

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.rubiksco.eliya.RegisterActivity
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KProperty
import javax.xml.datatype.DatatypeConstants.SECONDS

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


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

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY



/*    interceptor.readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)*/


    val client =
        OkHttpClient
        .Builder()

            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                if (response.code() == 403) {

                    preference(Static.TokenName,"","")
                   // showToast("اعتبار حساب شما منقضی شده است لطفا مجددا وارد سامانه شوید",Toast.LENGTH_LONG)
                    val intent = Intent(this,RegisterActivity::class.java)
                    intent.putExtra("renewtoke","yes")
                    startActivity(intent)

                    //   handleForbiddenResponse()
                }
                response
            }
            .addInterceptor(interceptor)
            .build()


        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(baseurl)
            .client(client)
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

fun  Context.GetToken(): String {

    val token:String= preference(Static.TokenName)


    return "bearer $token"
}


class TokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code() == 403) {
        //    doSomething()

        }
        return response
    }
}