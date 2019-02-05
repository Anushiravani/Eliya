package com.rubiksco.eliya.Models

import com.google.gson.annotations.SerializedName

data class ResponseRegister(

    @SerializedName("status")
    var status: Boolean,

    @SerializedName("msg")
    var msg: String



)