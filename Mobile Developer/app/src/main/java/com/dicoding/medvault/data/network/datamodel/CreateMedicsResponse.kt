package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class CreateMedicsDataResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("nomor_kunjungan") val visitNumber: String,
    @SerializedName("tanggal_kunjungan") val visitDate: String,
    @SerializedName("nama_pasien") val patientName: String,
    @SerializedName("anamnesa") val anamnesa: String,
    @SerializedName("hasil_diagnosa") val diagnosisResult: String,
    @SerializedName("tindakan") val action: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class CreateMedicsResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: CreateMedicsDataResponse
)