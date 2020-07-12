package com.emami.moviedb.common.db

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class DateConverterTest {

    private val dateConverter = DateConverter()

    @Test
    fun `should successfully convert instant to long`() {
        val now = Instant.now()

        assertEquals(dateConverter.fromInstant(now), now.toEpochMilli())
    }

    @Test
    fun `should successfully convert back long to instant`() {
        val now = Instant.now()
        assertEquals(dateConverter.toInstant(now.toEpochMilli()), now)
    }
}