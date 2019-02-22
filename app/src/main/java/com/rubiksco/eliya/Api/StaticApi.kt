package com.rubiksco.eliya.Api

import com.rubiksco.eliya.Models.AboutUsModel
import com.rubiksco.eliya.Models.UserModel
import io.reactivex.Observable
import retrofit2.Call

import retrofit2.http.*

interface  StaticApi{



    @FormUrlEncoded
    @POST("shopsapi/getstaticvalue/")
      fun getAboutUs(
        @Header("Authorization") headre: String,
        @Field("key") key: String
    ): Observable<AboutUsModel>

    @GET("getcrmadmin")

    fun GetUsers(): Observable<ArrayList<UserModel>>


    @GET("getcrmadmin")
    fun GetUserscall() : Call<ArrayList<UserModel>>



    //getcrmadmin

}