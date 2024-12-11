package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: UserData
)

data class UserData(
    @SerializedName("id") val id: Int,
    @SerializedName("imgprofile") val imgProfile: String?,
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("username") val username: String = "",
    @SerializedName("password") val password: String? = "",
    @SerializedName("alamat") val alamat: String = "",
    @SerializedName("email_verified_at") val emailVerifiedAt: String? = "",
    @SerializedName("created_at") val createdAt: String? = "",
    @SerializedName("updated_at") val updatedAt: String? = ""
)