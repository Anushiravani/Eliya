package com.rubiksco.eliya.Models

import com.google.gson.annotations.SerializedName

data class  SendResponseModel(

    @SerializedName("success")
    var count:Boolean,
    @SerializedName("msg")
    var msg:String



    )