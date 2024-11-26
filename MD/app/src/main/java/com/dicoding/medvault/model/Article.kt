package com.dicoding.medvault.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: String = "",
    val lastUpdate: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val informationTitle: String = "",
    val informationDescription: String = ""
) : Parcelable