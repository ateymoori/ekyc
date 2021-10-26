package com.lib.ekyc.presentation.utils.base

class DateUtil {
    companion object {

        fun convertDate(year: Int, month: Int, day: Int): String {
            return "${year.toString().takeLast(2)}${month.fixZero()}${day.fixZero()}"
        }

    }
}