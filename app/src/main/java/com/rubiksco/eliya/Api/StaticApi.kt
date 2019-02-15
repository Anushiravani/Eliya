package com.rubiksco.eliya.Api

import com.rubiksco.eliya.Models.AboutUsModel
import io.reactivex.Observable

import retrofit2.http.*

interface  StaticApi{



    @FormUrlEncoded
    @POST("shopsapi/getstaticvalue/")
      fun getAboutUs(
        @Header("Authorization") headre: String,
        @Field("key") key: String
    ): Observable<AboutUsModel>





}