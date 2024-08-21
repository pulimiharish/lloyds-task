package com.lbg.data.mapper

import com.lbg.data.mockApiFilm1
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilmDetailApiToDomainMapperTest {

    private lateinit var mapper: FilmDetailApiToDomainMapper

    @Before
    fun setUp() {
        mapper = FilmDetailApiToDomainMapper()
    }

    @Test
    fun `test mapper maps Api object to data object`() {
        // When
        val film = mapper.map(mockApiFilm1())

        // Then
        assertEquals(123L, film.id)
        assertEquals("Iron Man", film.title)
        assertEquals("This is about Iron man the superhero", film.overview)
        assertEquals("https://image.tmdb.org/t/p/w500/ironman.jpg", film.image)
        assertEquals("8.2", film.voteAverage)
    }

    @Test(timeout = 100)
    fun `test there is no timeout in the mapper method`() {
        // Given
        val apiFilm = mockApiFilm1()

        mapper.map(apiFilm)
    }
}