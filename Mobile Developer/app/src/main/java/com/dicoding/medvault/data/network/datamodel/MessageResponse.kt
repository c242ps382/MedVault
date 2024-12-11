package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("message") val message: String
)