package com.dicoding.medvault.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kunjungan(
    val id: Int,
    val visitNumber: String,
    val name: String,
    val address: String,
    val phoneNumber: String,
    val date: String,
    val diagnosis: String? = null,
    val description: String? = null,
    val type: String
) : Parcelable