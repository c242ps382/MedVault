package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class Anamnesa(
    @SerializedName("sistole_diastol") val sistoleDiastol: String,
    @SerializedName("suhu") val suhu: String,
    @SerializedName("detak_nadi") val detakNadi: String,
    @SerializedName("berat_badan") val beratBadan: String,
    @SerializedName("tinggi_badan") val tinggiBadan: String
)