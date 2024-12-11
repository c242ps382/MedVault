package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<ArticleItemResponse>
)

data class ArticleItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("update")
    val update: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("imageurl")
    val imageUrl: String,
    @SerializedName("infotitle")
    val infoTitle: String,
    @SerializedName("description")
    val description: String
)