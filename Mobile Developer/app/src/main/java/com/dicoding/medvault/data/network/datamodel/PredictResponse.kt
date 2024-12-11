package com.dicoding.medvault.data.network.datamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("disease")
    val disease: String,
    @SerializedName("probability")
    val probability: Double
) : Parcelable