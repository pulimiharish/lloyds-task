package com.lbg.data.mapper

import com.lbg.data.emptyApiVideoResponse
import com.lbg.data.mockApiVideoResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilmVideoApiToDomainMapperTest {

    private lateinit var mapper: FilmVideoApiToDomainMapper

    @Before
    fun setUp() {
        mapper = FilmVideoApiToDomainMapper()
    }

    @Test
    fun `test mapper maps Api object to data object`() {
        // When
        val video = mapper.map(mockApiVideoResponse())

        // Then
        assertEquals("12345", video.key)
    }

    @Test
    fun `test mapper maps Api object to empty object`() {
        // When
        val video = mapper.map(emptyApiVideoResponse())

        // Then
        assertEquals("", video.key)
    }

    @Test(timeout = 100)
    fun `test there is no timeout in the mapper method`() {
        // Given
        val apiVideoResponse = mockApiVideoResponse()

        mapper.map(apiVideoResponse)
    }
}