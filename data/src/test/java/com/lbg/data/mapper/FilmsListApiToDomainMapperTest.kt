package com.lbg.data.mapper

import com.lbg.data.mockApiFilmListResponse
import com.lbg.data.model.ApiFilmListResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FilmsListApiToDomainMapperTest {

    private lateinit var mapper: FilmsListApiToDomainMapper
    private lateinit var apiFilmListResponse: ApiFilmListResponse

    @Before
    fun setUp() {
        // Given
        mapper = FilmsListApiToDomainMapper()
        apiFilmListResponse = mockApiFilmListResponse()
    }

    @Test
    fun `test mapper maps Api object to data object`() {

        // When
        val filmList = mapper.map(apiFilmListResponse)

        // Then
        assertEquals(2, filmList.size)

        val film1 = filmList[0]
        assertNotNull(film1)
        // Then
        film1.apply {
            assertEquals(123L, id)
            assertEquals("Iron Man", title)
            assertEquals("This is about Iron man the superhero", overview)
            assertEquals("https://image.tmdb.org/t/p/w500/ironman.jpg", image)
            assertEquals("8.2", voteAverage)
        }
    }

    @Test(timeout = 100)
    fun `test there is no timeout in the mapper method`() {
        // Given
        val apiFilmListResponse = mockApiFilmListResponse()

        mapper.map(apiFilmListResponse)
    }

    @Test
    fun `test mapper maps multiple objects correctly`() {
        // Given
        val apiFilmListResponse = mockApiFilmListResponse()

        // When
        val filmList = mapper.map(apiFilmListResponse)

        // Then
        assertEquals(2, filmList.size)
        assertTrue(filmList[0].id == 123L)
        assertTrue(filmList[1].id == 456L)
    }

    @Test
    fun `test the film image is constructed correctly from the poster path`() {
        // Given
        val apiFilmListResponse = mockApiFilmListResponse()

        // When
        val filmList = mapper.map(apiFilmListResponse)

        // Then
        val film = filmList[0]
        assertNotNull(film)
        assertEquals("https://image.tmdb.org/t/p/w500/ironman.jpg", film.image)
    }
}