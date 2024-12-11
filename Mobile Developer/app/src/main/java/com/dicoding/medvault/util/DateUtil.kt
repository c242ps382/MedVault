package com.dicoding.medvault.util

import android.icu.text.SimpleDateFormat
import java.util.Locale

object DateUtil {

    fun convertToFormattedDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSz", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

        val date = inputFormat.parse(inputDate)

        return if (date != null) {
            outputFormat.format(date)
        } else {
            "Invalid Date"
        }
    }

    fun convertToFormattedDateDefault(inputDate: String): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

        val date = inputFormat.parse(inputDate)

        return if (date != null) {
            outputFormat.format(date)
        } else {
            "Invalid Date"
        }
    }
}