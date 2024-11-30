package com.dicoding.medvault.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kunjungan(
    val id: String,
    val name: String,
    val address: String,
    val phoneNumber: String,
    val date: String,
    val diagnosis: String? = null,
    val description: String? = null
) : Parcelable