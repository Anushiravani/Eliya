package com.rubiksco.eliya.Models

import com.google.gson.annotations.SerializedName

data class  UserModel(

    @SerializedName("Id")
    var Id:Int,
    @SerializedName("Name")
    var Name:String

)