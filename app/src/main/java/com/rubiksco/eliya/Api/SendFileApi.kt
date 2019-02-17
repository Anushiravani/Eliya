package com.rubiksco.eliya.Api

import com.rubiksco.eliya.Models.SendResponseModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.http.Multipart



interface SendFileApi {



    @Multipart
    @POST("form/")
    fun SendFile(
        @Header("Authorization") headre: String,
        //@Part List<MultipartBody.Part> file,
        @Part surveyImage: ArrayList<MultipartBody.Part>,
      //  @Part propertyImage: MultipartBody.Part,
        @Part("contractcode") contractCode: String,
        @Part("adminname") adminname: String

    ): Observable<SendResponseModel>




}