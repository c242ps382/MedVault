package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    val token: String,
    @SerializedName("user")
    val user: UserData
)
