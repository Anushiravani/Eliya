package com.rubiksco.eliya.Api

import com.rubiksco.eliya.Models.ResponseRegister
import com.rubiksco.eliya.Models.SearchModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface  RegisterApi{


    @Headers("Content-Type:application/x-www-form-urlencoded", "Accept:application/json")
    @FormUrlEncoded
    @POST("signupwithapi")
    fun SignUp(@Field("number") number: String): Observable<ResponseRegister>


}