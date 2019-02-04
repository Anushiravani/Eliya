package com.rubiksco.eliya

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import com.rubiksco.eliya.Adapter.SearchAdapter
import com.rubiksco.eliya.Api.SearchApi
import com.rubiksco.eliya.Models.SearchModel
import com.rubiksco.eliya.Static.GetRetrofit
import com.rubiksco.eliya.Static.Static
import com.rubiksco.eliya.Static.hideKeyboard
import com.rubiksco.eliya.Static.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import android.view.ViewGroup
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import com.rubiksco.eliya.Static.Static.hideSoftKeyboard


class MainActivity : AppCompatActivity() {


    private lateinit var searchAdapter : SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


     // val retrofit :Retrofit = this.GetRetrofit()
     /*   val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(Static.StiteUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
*/


        searchAdapter = SearchAdapter(this)
        movies_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        movies_list.adapter = searchAdapter


        UpdateResultSearch();

        button.setOnClickListener {


            UpdateResultSearch(search_txt.text.toString(),true)



        }
        swipe.setOnRefreshListener {

            UpdateResultSearch(search_txt.text.toString(),true)


        }


       // movies_list.layoutManager =
    }



    fun UpdateResultSearch(query:String?="", forceUpdate:Boolean=false){
        val retrofit :Retrofit = this.GetRetrofit()
        swipe.setRefreshing(true);
        hideKeyboard()

        var apisearch = retrofit.create(SearchApi::class.java)
        apisearch.SearchPage(query!!)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //   var list : SearchModel=it



                    if(forceUpdate){
                        searchAdapter.UpdateItems(it.list)
                        showToast(query)
                    }

                    else{
                        searchAdapter.setItems(it.list)
                    }

                    swipe.setRefreshing(false);

                },{


                    showToast(it.message)
                })

    }
}
