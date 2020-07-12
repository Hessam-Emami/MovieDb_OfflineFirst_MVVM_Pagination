package com.emami.moviedb.common.util

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateTimeUtilTest {

    @Test
    fun `should convert date to formatted string`() {
        //because it starts from 0
        val date = Calendar.getInstance().apply { set(2010, 10, 22, 10, 10) }.time

        val result = DateTimeUtil.formatDateToString(date)

        assertEquals(result, "2010-11-22")
    }

    @Test
    fun `should parse date string to ui date`() {
        val date = "2010-03-10"

        val result = DateTimeUtil.getYearMonthFromDateString(date)

        assertEquals(result.toLowerCase(), "March 2010".toLowerCase())
    }
}