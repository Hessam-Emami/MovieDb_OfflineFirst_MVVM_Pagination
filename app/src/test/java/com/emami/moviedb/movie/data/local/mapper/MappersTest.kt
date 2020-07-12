package com.emami.moviedb.movie.data.local.mapper

import com.emami.moviedb.movie.data.network.dto.MovieDTO
import junit.framework.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `should successfully convert network movie to local movie`() {
        val dto = MovieDTO(11, "", "", 1.1, false, false, "", 1, "", "", "", listOf(), 1.1, "")

        val entity = dto.toMovieEntity()

        assertEquals(entity.id, 11)
        assertEquals(entity.popularity, 1.1)
    }

}