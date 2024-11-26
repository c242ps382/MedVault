
package com.dicoding.medvault.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    val id: String = "",
    val name: String,
    val address: String,
    val phoneNumber: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val religion: String = "",
    val nik: String = "",
    val patientDate: String = ""
) : Parcelable

