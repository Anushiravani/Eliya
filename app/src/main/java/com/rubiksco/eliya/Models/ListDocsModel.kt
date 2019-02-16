package com.rubiksco.eliya.Models

import com.google.gson.annotations.SerializedName

data class ListDocsModel(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("list")
    var list: List<DocsItem>
)

data class DocsItem(
    @SerializedName("title")
    var title: String?,
    @SerializedName("url")
    var url: String?
)