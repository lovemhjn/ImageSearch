package com.app.imagesearch.util

import com.app.imagesearch.constants.AppConstants
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun formatDate(
        date: String?
    ): String {
        return try {
            val inputFormat = SimpleDateFormat(AppConstants.DATE_FORMAT_SERVER, Locale.US)
            val inputDate = inputFormat.parse(date)
            val outputFormat = SimpleDateFormat(AppConstants.DATE_FORMAT_DISPLAY, Locale.US)
            outputFormat.format(inputDate)
        } catch (ex: Exception) {
            ""
        }
    }

}