package com.lib.ekyc.presentation.utils.base

import junit.framework.TestCase
import org.junit.Test


//PAN should involves letter and digits
// size should be in 8..14
class DocumentValidationUtilTest {

    @Test
    fun `correct_PAN_should_true`() {
        TestCase.assertEquals(
            true,
            DocumentValidationUtil.passportNumberIsValid("NMJ7628332")
        )
    }

    @Test
    fun `incorrect_PAN_just_digits_should_false`() {
        TestCase.assertEquals(
            false,
            DocumentValidationUtil.passportNumberIsValid("123456789")
        )
    }

    @Test
    fun `incorrect_PAN_just_letter_should_false`() {
        TestCase.assertEquals(
            false,
            DocumentValidationUtil.passportNumberIsValid("asdfghjk")
        )
    }

    @Test
    fun `incorrect_PAN_contains_special_chars_should_false`() {
        TestCase.assertEquals(
            false,
            DocumentValidationUtil.passportNumberIsValid("wygydw@#234")
        )
    }

    @Test
    fun `SHORT_PAN_contains_should_false`() {
        TestCase.assertEquals(
            false,
            DocumentValidationUtil.passportNumberIsValid("we1234")
        )
    }

    @Test
    fun `LONG_PAN_contains_should_false`() {
        TestCase.assertEquals(
            false,
            DocumentValidationUtil.passportNumberIsValid("we1234qwe123abnmv")
        )
    }
    @Test
    fun `EMPTY_PAN_contains_should_false`() {
        TestCase.assertEquals(
            false,
            DocumentValidationUtil.passportNumberIsValid("")
        )
    }

}