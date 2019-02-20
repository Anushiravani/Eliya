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

import com.rubiksco.eliya.Adapter.pixiAdapter

import kotlinx.android.synthetic.main.activity_send.*
import kotlinx.android.synthetic.main.content_send.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View


import android.widget.ArrayAdapter
import com.rubiksco.eliya.Api.SendFileApi

import com.rubiksco.eliya.Static.GetRetrofit
import com.rubiksco.eliya.Static.GetToken

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File
import android.os.Build
import android.widget.ProgressBar
import com.rubiksco.eliya.Others.ProgressRequestBody
import com.rubiksco.eliya.Static.Static
import com.rubiksco.eliya.Static.showToast


class SendActivity : AppCompatActivity()   {




    /*override fun onProgressUpdate(percentage: Int) {
        progressBar.progress = percentage;
    }

    override fun onError() {

        showToast("ERROOORRR")
    }

    override fun onFinish() {
        showToast("SUCCESS")

    }*/

    private   val READ_REQUEST_CODE: Int = 42

    lateinit var typedocs  :String

    val listImageUploade  :HashSet<String> =HashSet()


    lateinit var  pixiAdapter:pixiAdapter
    val isSending:Boolean=false

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {




                if (data!!.clipData != null) {
                    val count = data.clipData!!.itemCount
                    var currentItem = 0
                    val listim  :ArrayList<String> =ArrayList<String>()
                    while (currentItem < count) {
                        val imageUri = data.clipData!!.getItemAt(currentItem).uri

                        listim.add(imageUri.toString())
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        currentItem += 1
                    }


                    pixiAdapter.addImage(listim)

                    textselectimage.text="در صورت نیاز عکس های دیگر ضمیمه کنید"

                    boxsend.visibility = View.VISIBLE


                    listImageUploade.addAll(listim)



                } else if (data.data != null) {
                  ///  val imagePath = data.data!!.path
                    val listim  :ArrayList<String> =ArrayList<String>()
               //     listim.add(imagePath.toString())

                    data.data?.also { uri ->
                        listim.add(uri.toString())
                    }

                  //  val   imagePath   = data.clipData!!.getItemAt(0).uri

                    pixiAdapter.addImage(listim)
                    textselectimage.text="در صورت نیاز عکس های دیگر ضمیمه کنید"
                    listImageUploade.addAll(listim)
                    boxsend.visibility = View.VISIBLE
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        progressBar.progress = 20;
        typedocs= intent.getStringExtra("title")

        fab.setOnClickListener { view ->

            if (!isSending){


                performFileSearch()


            }


        }
        val mLayoutManager = GridLayoutManager(this, 2)

        recycleimage.layoutManager = mLayoutManager
          pixiAdapter =pixiAdapter(this)
        recycleimage.adapter = pixiAdapter;

        addItemsOnSpinner2()
        docTitle.text= typedocs


        sendodcsbtn.setOnClickListener {  SendDocs()}
    }
    fun performFileSearch() {

        val mimeTypes = arrayOf("image/*", "application/pdf")

        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE);
       // intent.type = "*/*" //allows any image file type. Change * to specific extension to limit it
//**These following line is the important one!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
            if (mimeTypes.isNotEmpty()) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
        } else {
            var mimeTypesStr = ""

            for (mimeType in mimeTypes) {
                mimeTypesStr += "$mimeType|"
            }

            intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
        }


        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),

            READ_REQUEST_CODE
        ) //SELECT_PICTURES is simply a global int used to check the calling intent in onActivityResult


       // startActivityForResult(intent, READ_REQUEST_CODE)
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
            val file = File(listImageUploade.toArray()[index].toString())
            val surveyBody = RequestBody.create(MediaType.parse("./*"), file)
            surveyImagesParts.add( MultipartBody.Part.createFormData("SurveyImage $index", file.name, surveyBody))
        }

        var retrofit : Retrofit = GetRetrofit(Static.SiteApiUrl)
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
