package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class GetPatientResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<PatientData>
)
