package com.lib.ekyc.presentation.utils.base

import junit.framework.TestCase
import org.junit.Test

class DateUtilTest {


    @Test
    fun `correct_date_should_true`() {
        TestCase.assertEquals("201201" ,DateUtil.convertDate(year = 2020, month = 12, day = 1))
    }

    @Test
    fun `incorrect_date_should_false`() {
        TestCase.assertNotSame("20121" ,DateUtil.convertDate(year = 2020, month = 12, day = 1))
    }

}