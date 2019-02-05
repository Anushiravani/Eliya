package com.rubiksco.eliya.Api

import com.rubiksco.eliya.Models.ResponeLogin
import com.rubiksco.eliya.Models.ResponseRegister
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface  LoginApi{


/*    @Headers("Content-Type:application/x-www-form-urlencoded", "Accept:application/json")
    @FormUrlEncoded
    @POST("signupwithapi")
    fun SignUp(@Field("number") number: String): Observable<ResponseRegister>*/


    @Headers("Content-Type:application/x-www-form-urlencoded", "Accept:application/json")
    @FormUrlEncoded
    @POST("token")
      fun Login(
        @Field("grant_type") grant_type: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<ResponeLogin>


}