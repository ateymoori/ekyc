package com.lib.ekyc.presentation.utils.base

import com.lib.ekyc.presentation.utils.fixZero
import com.lib.ekyc.presentation.utils.log

class DateUtil {
    companion object {

        fun convertDate(year: Int, month: Int, day: Int): String {
            return "${year.toString().takeLast(2)}${month.fixZero()}${day.fixZero()}"
        }

    }
}