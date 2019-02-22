package com.rubiksco.eliya.Static

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.rubiksco.eliya.RegisterActivity
import ninja.sakib.pultusorm.core.PultusORM
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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



fun  Context.GetDb():PultusORM{


    return PultusORM(
        "database.db"
        ,  applicationContext.filesDir.absolutePath
        // , "/Users/s4kib/"
    )
}


fun  Context.GetToken(): String {

    val token:String= preference(Static.TokenName)


    return "bearer $token"
}

fun Context.getRealPathFromUri(uri: Uri ): String {
    // DocumentProvider
    val mContext = this

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(mContext, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().path + "/" + split[1]
            }
        } else if (isDownloadsDocument(uri)) {

            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
            )

            return getDataColumn(mContext, contentUri, null, null)
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            var contentUri: Uri? = null
            if ("image" == type) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])

            return getDataColumn(mContext, contentUri!!, selection, selectionArgs)
        }// MediaProvider
        // DownloadsProvider
    } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {

        // Return the remote address
        return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(mContext, uri, null, null)

    } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
        return uri.path
    }// File
    // MediaStore (and general)

    return ""
}

private fun getDataColumn(
    context: Context, uri: Uri, selection: String?,
    selectionArgs: Array<String>?
): String {

    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
        cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
        if (cursor != null && cursor!!.moveToFirst()) {
            val index = cursor!!.getColumnIndexOrThrow(column)
            return cursor!!.getString(index)
        }
    } finally {
        if (cursor != null)
            cursor!!.close()
    }
    return ""
}

private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

private fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
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