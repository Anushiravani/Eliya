package com.rubiksco.eliya.Api

import com.rubiksco.eliya.Models.SearchModel
import io.reactivex.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query


interface  SearchApi{


    @GET("/searchpages")

    fun SearchPage(@Query("id")  query :String =""):Observable<SearchModel>





}