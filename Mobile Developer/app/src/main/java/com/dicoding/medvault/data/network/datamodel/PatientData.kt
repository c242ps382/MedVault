package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class PatientData(
    @SerializedName("nama") val name: String,
    @SerializedName("nik") val nik: String,
    @SerializedName("tanggal_lahir") val tanggalLahir: String,
    @SerializedName("alamat") val alamat: String,
    @SerializedName("jenis_kelamin") val jenisKelamin: String,
    @SerializedName("umur") val umur: String,
    @SerializedName("nomor_hp") val nomorHp: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: Int
)