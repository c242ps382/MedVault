package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class PredictRequest(
    @SerializedName("symptoms")
    val symptoms: List<String> = emptyList()
)
