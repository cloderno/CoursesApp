package com.yeldar.common.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))

    fun format(date: String): String {
        return try {
            val dateObj = inputFormat.parse(date)
            dateObj?.let { outputFormat.format(it) } ?: date
        } catch (e: Exception) {
            date
        }
    }
}