package com.rubiksco.eliya.Models

import com.google.gson.annotations.SerializedName

data class SearchModel(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("list")
    var list: List<SearchItem>
)

data class SearchItem(
    @SerializedName("title")
    var title: String?,
    @SerializedName("url")
    var url: String?
)