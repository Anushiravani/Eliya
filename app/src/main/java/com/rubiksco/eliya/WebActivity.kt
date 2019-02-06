package com.rubiksco.eliya


import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_web.*

import android.graphics.Bitmap
import com.rubiksco.eliya.Static.Static
import com.rubiksco.eliya.Static.showToast
import android.os.Build
import android.annotation.TargetApi
import android.webkit.*


class WebActivity : AppCompatActivity() {

    lateinit var url :String
    lateinit var title :String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        forceRTLIfSupported()
        url= intent.getStringExtra("url")
        title= intent.getStringExtra("title")

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }

*/
/*

        setTitle(title)
        assert(supportActionBar != null)   //null check
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)   //show back button
        supportActionBar!!.setHomeButtonEnabled(true);
*//*



        webview.settings.javaScriptEnabled = true
        webview.settings.loadWithOverviewMode = true
        webview.settings.javaScriptCanOpenWindowsAutomatically=true
        webview.settings.useWideViewPort=true
        webview.setInitialScale(1)
        webview.settings.setAppCacheEnabled(true)
        webview.settings.builtInZoomControls = true
*/


//        webview.webChromeClient=object  : WebChromeClient() {}

        webview.loadUrl(url)
      //LoadUrl(url)



        swipeweb.setOnRefreshListener {    LoadUrl(url) }





    }

    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        //finish()
        return true
    }
    private fun LoadUrl(   theUrl:String=""){

        webprogressbar.visibility= View.VISIBLE

        val urls:String? = if (theUrl.contains("http"))
            theUrl
        else
            Static.StiteUrl+theUrl

        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                setProgressBarVisibility(View.VISIBLE)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                setProgressBarVisibility(View.GONE)
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                showToast("لود صفحه با خطا مواجه شد . لطفا مجدد بررسی کنید")

                setProgressBarVisibility(View.GONE)
            }
        }

        webview.loadUrl(urls)
    }
    private fun setProgressBarVisibility(visibility: Int) {

            webprogressbar.visibility = visibility
        swipeweb.isRefreshing=false

    }


}
