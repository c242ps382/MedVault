package com.dicoding.medvault.data.network.datamodel

import com.google.gson.annotations.SerializedName

data class CreateNonMedicsRequest(
    @SerializedName("nomor_kunjungan") val visitNumber: String,
    @SerializedName("tanggal_kunjungan") val visitDate: String,
    @SerializedName("nama_pasien") val patientName: String,
    @SerializedName("keluhan") val complaint: String,
    @SerializedName("tindakan") val action: String,
    @SerializedName("biaya_pembayaran") val paymentCost: Int
)