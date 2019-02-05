package com.rubiksco.eliya

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rubiksco.eliya.Api.LoginApi
import com.rubiksco.eliya.Static.GetRetrofit
import com.rubiksco.eliya.Static.Static
import com.rubiksco.eliya.Static.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {


    lateinit var number :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        number= intent.getStringExtra("number")


        signin_btn.setOnClickListener {
            singIn(code_txt.text.toString())
        }

    }

    @SuppressLint("CheckResult")
     private fun singIn(code: String?="") {
        if (code.isNullOrEmpty()) showToast(getString(R.string.EnterTheCode), Toast.LENGTH_SHORT)

        val retrofit:Retrofit=GetRetrofit(Static.SiteApiUrl)
        val loginApi:LoginApi= retrofit.create(LoginApi::class.java)

        loginApi.Login(Static.grant_type,number,code!!)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //swipe.setRefreshing(false);
                var res=it;





            },{
                showToast(it.message,Toast.LENGTH_SHORT)
            })


    }
}
