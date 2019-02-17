package com.rubiksco.eliya

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.rubiksco.eliya.Adapter.pixiAdapter

import kotlinx.android.synthetic.main.activity_send.*
import kotlinx.android.synthetic.main.content_send.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

import android.support.v7.widget.GridLayoutManager

import android.view.View
import android.widget.ArrayAdapter
import com.rubiksco.eliya.Api.SendFileApi
import com.rubiksco.eliya.Models.SendResponseModel
import com.rubiksco.eliya.Static.GetRetrofit
import com.rubiksco.eliya.Static.GetToken
import com.rubiksco.eliya.Static.Static
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_about_us.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File


class SendActivity : AppCompatActivity() {


    lateinit var typedocs  :String
    lateinit var listImageUploade :ArrayList<String>
    lateinit var  pixiAdapter:pixiAdapter
    val isSending:Boolean=false

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 222) {
            val returnValue = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)

          //  val returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            pixiAdapter.addImage(returnValue)
            listImageUploade= returnValue

            textselectimage.text="در صورت نیاز عکس های دیگر ضمیمه کنید"

            boxsend.visibility = View.VISIBLE
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(this, 222, 10)
                } else {
                    Toast.makeText(this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG)
                        .show()
                }
                return
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)

        typedocs= intent.getStringExtra("title")

        fab.setOnClickListener { view ->

            if (!isSending){
                Pix.start(this, 222, 10);

            }

            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
        }
        val mLayoutManager = GridLayoutManager(this, 2)

        recycleimage.layoutManager = mLayoutManager
          pixiAdapter =pixiAdapter(this)
        recycleimage.adapter = pixiAdapter;

        addItemsOnSpinner2()
        docTitle.text= typedocs


        sendodcsbtn.setOnClickListener {  SendDocs()}
    }

    fun addItemsOnSpinner2() {


        val list = ArrayList<String>()

        list.add("خانم بهشتی")
        list.add("خانم شفیغی")
        list.add("خانم بهرامی")
        list.add("خانم فرجام")
        list.add("خانم ناخدازاده")

        val dataAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectd.setAdapter(dataAdapter)
    }

    fun SendDocs(){


        requestUploadSurvey()



    }

    @SuppressLint("CheckResult")
    private fun requestUploadSurvey() {
        //val propertyImageFile = File(surveyModel.getPropertyImagePath())
       // val propertyImage = RequestBody.create(MediaType.parse("image/*"), propertyImageFile)
        //val propertyImagePart =   MultipartBody.Part.createFormData("PropertyImage", propertyImageFile.getName(), propertyImage)


        val surveyImagesParts= ArrayList<MultipartBody.Part>(  )

        for (index in 0 until listImageUploade.size) {
           /* Log.d(
                FragmentActivity.TAG,
                "requestUploadSurvey: survey image " + index + "  " + surveyModel.getPicturesList().get(index).getImagePath()
            )*/
            val file = File(listImageUploade[index])
            val surveyBody = RequestBody.create(MediaType.parse("image/*"), file)
            surveyImagesParts.add( MultipartBody.Part.createFormData("SurveyImage $index", file.name, surveyBody))
        }

        var retrofit : Retrofit = GetRetrofit("http://192.168.3.154:1407/")
        val webServicesAPI = retrofit.create(SendFileApi::class.java)


        webServicesAPI.SendFile(GetToken(),surveyImagesParts,"contractcode","sss")
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //   var list : SearchModel=it

                val res = it





            },{



            })

       /* //val webServicesAPI = RetrofitManager.getInstance().getRetrofit().create(WebServicesAPI::class.java)
        var surveyResponse: Observable<SendResponseModel>? = null
        if (surveyImagesParts != null) {
            surveyResponse = webServicesAPI.SendFile(Static.tok)
        }
        surveyResponse!!.enqueue(this)*/
    }
}
