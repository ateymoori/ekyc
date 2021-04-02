package com.lib.ekyc.presentation.utils.base

import java.util.regex.Pattern

class DocumentValidationUtil {

    companion object {

        fun passportNumberIsValid(passportNumber: String?): Boolean {
            if (passportNumber?.length !in 8..14) return false

            val letter = Pattern.compile("[a-zA-z]")
            val digit = Pattern.compile("[0-9]")
            val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")

            val hasLetter = letter.matcher(passportNumber)
            val hasDigit = digit.matcher(passportNumber)
            val hasSpecial = special.matcher(passportNumber)

            return hasLetter.find() && hasDigit.find() && !hasSpecial.find()
        }
    }
}