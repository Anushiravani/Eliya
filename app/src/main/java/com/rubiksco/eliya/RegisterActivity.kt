package com.rubiksco.eliya

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.rubiksco.eliya.Api.RegisterApi
import com.rubiksco.eliya.Static.GetRetrofit
import com.rubiksco.eliya.Static.Static
import com.rubiksco.eliya.Static.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Retrofit
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper



class RegisterActivity : AppCompatActivity() {


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


       // number= intent.getStringExtra("number")+"c"


        if (intent.getStringExtra("renewtoke")=="yes"){
            showToast("اعتبار حساب شما منقضی شده است لطفا مجددا وارد سامانه شوید",Toast.LENGTH_LONG)
        }

        signUp_btn.setOnClickListener {
            SignUp(number_txt.text.toString())
        }

        beforeregistered.setOnClickListener {
            gotologinwithnumber(number_txt.text.toString())

        }

    }
    @SuppressLint("CheckResult")

    private fun gotologinwithnumber(number: String=""){


       if(!checKmobile(number)) return

        val intent = Intent(this, LoginActivity::class.java) //not application context
        intent.putExtra("number",number)
        startActivity(intent)


    }

   @SuppressLint("CheckResult")
   private fun SignUp(number:String=""){

       if(!checKmobile(number)) return


       var retrofit :Retrofit = GetRetrofit(Static.SiteApiUrl)
        val apiRegister = retrofit.create(RegisterApi::class.java)
        progressBarRegister.visibility = View.VISIBLE
        signUp_btn.isEnabled = false;

        apiRegister.SignUp(number)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if (it.status){
                    showToast("انجام شد:",Toast.LENGTH_SHORT)

                    val intent = Intent(this, LoginActivity::class.java) //not application context
                    intent.putExtra("number",number)
                    startActivity(intent)



                }else{
                    showToast("خطا:"+it.msg,Toast.LENGTH_SHORT)
                }

                progressBarRegister.visibility = View.GONE
                signUp_btn.isEnabled = true;


            },{

            })
    }

    fun  checKmobile(number :String=""):Boolean{
        if(number ==""){
            showToast("ابتدا شماره موبایل خود را وارد نمایید", Toast.LENGTH_SHORT)
            return false
        }

        if (!number.matches("(\\+98|0)?9\\d{9}".toRegex())) {
            showToast("شماره موبایل نامعتبر هست", Toast.LENGTH_SHORT)

            return false

        }
        return true
    }
}
