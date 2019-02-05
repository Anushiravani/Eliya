package com.rubiksco.eliya

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.rubiksco.eliya.Adapter.SearchAdapter
import com.rubiksco.eliya.Api.SearchApi

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Retrofit

import android.view.View
import com.rubiksco.eliya.Static.*

class MainActivity : AppCompatActivity() {


    private lateinit var searchAdapter : SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        searchAdapter = SearchAdapter(this)
        movies_list.adapter = searchAdapter


      var lastsearch   =  preference("lastsearch")
        if (lastsearch!="") {
            UpdateResultSearch(lastsearch)
            search_txt.setText(lastsearch)
        }else
            UpdateResultSearch()








        button.setOnClickListener { UpdateResultSearch(search_txt.text.toString(),true) }
        swipe.setOnRefreshListener {UpdateResultSearch(search_txt.text.toString(),true) }


    }



    @SuppressLint("CheckResult")
    fun UpdateResultSearch(query:String?="", forceUpdate:Boolean=false){
        val retrofit :Retrofit = this.GetRetrofit()
        //swipe.setRefreshing(true);
        swipe.isRefreshing = false

        hideKeyboard()
        movies_list.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        preference("lastsearch",query!!,"")

        val apisearch = retrofit.create(SearchApi::class.java)


        apisearch.SearchPage(query!!)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //   var list : SearchModel=it
                    if(forceUpdate){
                        searchAdapter.UpdateItems(it.list)
                    }
                    else{
                        searchAdapter.setItems(it.list)
                    }                    //swipe.setRefreshing(false);
                    progressBar.visibility = View.GONE
                    movies_list.visibility = View.VISIBLE


                },{

                })

    }
}
