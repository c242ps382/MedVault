package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class UpdatePatientRequest(
    @SerializedName("nama") val nama: String,
    @SerializedName("nik") val nik: String,
    @SerializedName("tanggal_lahir") val tanggalLahir: String,
    @SerializedName("alamat") val alamat: String,
    @SerializedName("jenis_kelamin") val jenisKelamin: String,
    @SerializedName("umur") val umur: String,
    @SerializedName("nomor_hp") val nomorHp: String
)

data class UpdatePatientResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: PatientData
)