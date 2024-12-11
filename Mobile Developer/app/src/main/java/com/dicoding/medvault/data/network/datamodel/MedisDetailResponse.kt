package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class NonMedisDetailResponse(
    @SerializedName("user")
    val user: NonMedisUser,
    @SerializedName("kunjungan")
    val kunjungan: NonMedisKunjungan
)

data class MedisDetailResponse(
    @SerializedName("user")
    val user: NonMedisUser,
    @SerializedName("kunjungan")
    val kunjungan: MedisKunjungan
)

data class NonMedisUser(
    @SerializedName("nama")
    val nama: String,
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("jenis_kelamin")
    val jenisKelamin: String,
    @SerializedName("tanggal_lahir")
    val tanggalLahir: String,
    @SerializedName("umur")
    val umur: String
)

data class MedisKunjungan(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nomor_kunjungan")
    val nomorKunjungan: String,
    @SerializedName("tanggal_kunjungan")
    val tanggalKunjungan: String,
    @SerializedName("anamnesa")
    val anamnesa: String,
    @SerializedName("hasil_diagnosa")
    val hasilDiagnosa: String,
    @SerializedName("tindakan")
    val tindakan: String,
    @SerializedName("gejala")
    val gejala: String,
)

data class NonMedisKunjungan(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nomor_kunjungan")
    val nomorKunjungan: String,
    @SerializedName("tanggal_kunjungan")
    val tanggalKunjungan: String,
    @SerializedName("keluhan")
    val keluhan: String,
    @SerializedName("tindakan")
    val tindakan: String,
    @SerializedName("biaya_pembayaran")
    val biaya: String
)