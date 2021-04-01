package com.lib.ekyc.presentation.utils.base

import java.util.regex.Matcher
import java.util.regex.Pattern

class DocumentValidationUtil {

    companion object {

        fun passportNumberIsValid(passportNumber: String?): Boolean {

            if (passportNumber.isNullOrEmpty()) return false
            if (passportNumber.length !in 7..13) return false

            return (passportNumber.matches(Regex(".*[A-Za-z].*")) &&
            passportNumber.matches(Regex(".*[0-9].*")))


        }
    }


}