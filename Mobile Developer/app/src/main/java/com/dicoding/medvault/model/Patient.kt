
package com.dicoding.medvault.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    val id: Int = 0,
    val name: String,
    val address: String,
    val phoneNumber: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val age: String = "",
    val nik: String = "",
    val patientDate: String = ""
) : Parcelable

