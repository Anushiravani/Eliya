package com.rubiksco.eliya

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import com.rubiksco.eliya.Api.StaticApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_about_us.*
import retrofit2.Retrofit
import android.os.Build
import android.text.Spanned
import com.rubiksco.eliya.Static.*


class AboutUsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)


        getAboutUsContent();


        aboutprogress.visibility= View.VISIBLE

    }

    @SuppressLint("CheckResult")
    fun getAboutUsContent(){
        val retrofit : Retrofit = this.GetRetrofit()
        val api = retrofit.create(StaticApi::class.java)


        api.getAboutUs(GetToken(),"about")
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //   var list : SearchModel=it

                aboutustext.text= fromHtml(it.value!!)
                aboutprogress.visibility= View.GONE

            },{



            })

    }


    fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }
}
