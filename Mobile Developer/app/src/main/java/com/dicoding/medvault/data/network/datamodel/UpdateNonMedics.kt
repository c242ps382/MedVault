package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class UpdateNonMedicsResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: NonMedicsData
)

data class NonMedicsData(
    @SerializedName("id") val id: Int,
    @SerializedName("nomor_kunjungan") val visitNumber: String,
    @SerializedName("tanggal_kunjungan") val visitDate: String,
    @SerializedName("nama_pasien") val patientName: String,
    @SerializedName("keluhan") val complaint: String,
    @SerializedName("tindakan") val action: String,
    @SerializedName("biaya_pembayaran") val paymentCost: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class UpdateNonMedicsRequest(
    @SerializedName("nomor_kunjungan") val visitNumber: String,
    @SerializedName("tanggal_kunjungan") val visitDate: String,
    @SerializedName("nama_pasien") val patientName: String,
    @SerializedName("keluhan") val complaint: String,
    @SerializedName("tindakan") val action: String,
    @SerializedName("biaya_pembayaran") val paymentCost: Int
)