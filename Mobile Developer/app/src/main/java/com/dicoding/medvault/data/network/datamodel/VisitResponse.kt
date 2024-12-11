package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class VisitDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String?,
    @SerializedName("alamat") val address: String?,
    @SerializedName("nomor_hp") val phoneNumber: String?,
    @SerializedName("tanggal_kunjungan") val visitDate: String,
    @SerializedName("nomor_kunjungan") val visitNumber: String,
    @SerializedName("hasil_diagnosa") val diagnosisResult: String?,
    @SerializedName("tindakan") val action: String?,
    @SerializedName("keluhan") val complaint: String?,
    @SerializedName("biaya_pembayaran") val paymentCost: String?,
    @SerializedName("type") val type: String
)

data class VisitResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val visits: List<VisitDetail>
)