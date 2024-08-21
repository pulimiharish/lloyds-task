package com.lbg.data.repository.source

import com.lbg.data.api.FilmService
import com.lbg.data.mockApiFilm1
import com.lbg.data.mockFilm1
import com.lbg.data.emptyResponseBody
import com.lbg.data.mapper.FilmDetailApiToDomainMapper
import com.lbg.data.repository.ApiResponseHandler
import com.lbg.domain.shared.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FilmDetailsDataSourceImplTest {

    private lateinit var service: FilmService
    private lateinit var filmDetailApiToDomainMapper: FilmDetailApiToDomainMapper
    private lateinit var apiResponseHandler: ApiResponseHandler
    private lateinit var dataSource: FilmDetailsDataSource

    @Before
    fun setUp() {
        service = mockk()
        filmDetailApiToDomainMapper = mockk()
        apiResponseHandler = mockk()
        dataSource =
            FilmDetailsDataSourceImpl(service, filmDetailApiToDomainMapper, apiResponseHandler)
    }

    @Test
    fun `test getFilm returns success response`() = runTest {
        // Given
        coEvery { service.getMovie(123L) } returns Response.success(mockApiFilm1())
        coEvery { filmDetailApiToDomainMapper.map(any()) } returns mockFilm1()

        // When
        val result = dataSource.getFilm(123L).first()

        // Then
        assert(result is Result.Success)
    }

    @Test
    fun `test getFilm returns Error response`() = runTest {
        // Given
        coEvery { service.getMovie(321L) } returns Response.error(400, emptyResponseBody())

        // When
        val result = dataSource.getFilm(321L).first()

        // Then
        assert(result is Result.Error)
    }

    @Test
    fun `test getFilm returns an exception`() = runTest {
        // Given
        coEvery { service.getMovie(987L) } throws RuntimeException("Exception")

        // When
        val result = dataSource.getFilm(987L).first()

        // Then
        assert(result is Result.Error)
    }

}