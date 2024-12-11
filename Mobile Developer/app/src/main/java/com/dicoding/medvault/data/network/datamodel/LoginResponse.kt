package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserData,
)