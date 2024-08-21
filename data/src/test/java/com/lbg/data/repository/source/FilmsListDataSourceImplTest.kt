package com.lbg.data.repository.source

import com.lbg.data.api.FilmService
import com.lbg.data.mockFilmsList
import com.lbg.data.emptyResponseBody
import com.lbg.data.mapper.FilmsListApiToDomainMapper
import com.lbg.data.mockApiFilmListResponse
import com.lbg.data.repository.ApiResponseHandler
import com.lbg.domain.shared.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FilmsListDataSourceImplTest {

    private lateinit var service: FilmService
    private lateinit var filmsListApiToDomainMapper: FilmsListApiToDomainMapper
    private lateinit var apiResponseHandler: ApiResponseHandler
    private lateinit var dataSource: FilmsListDataSource

    @Before
    fun setUp() {
        service = mockk()
        filmsListApiToDomainMapper = mockk()
        apiResponseHandler = ApiResponseHandler()
        dataSource = FilmsListDataSourceImpl(service, filmsListApiToDomainMapper, apiResponseHandler)
    }

    @Test
    fun `test getFilmsList returns success`(): Unit = runTest {
        // Given
        coEvery { service.getMoviesList() } returns Response.success(mockApiFilmListResponse())
        coEvery { filmsListApiToDomainMapper.map(any()) } returns mockFilmsList()

        // When
        val result = dataSource.getFilmsList().first()

        // Then
        assert(result is Result.Success)
    }

    @Test
    fun `test getFilmsList returns Error response`() = runTest {
        // Given
        coEvery { service.getMoviesList() } returns Response.error(400, emptyResponseBody())

        // When
        val result = dataSource.getFilmsList().first()

        // Then
        assert(result is Result.Error)
    }

    @Test
    fun `test getFilmsList returns an exception`() = runTest {
        // Given
        coEvery { service.getMoviesList() } throws RuntimeException("Exception")

        // When
        val result = dataSource.getFilmsList().first()

        // Then
        assert(result is Result.Error)
    }

}