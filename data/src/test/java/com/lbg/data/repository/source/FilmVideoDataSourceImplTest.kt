package com.lbg.data.repository.source

import com.lbg.data.api.FilmService
import com.lbg.data.emptyApiVideoResponse
import com.lbg.data.mockApiFilm1
import com.lbg.data.mockFilm1
import com.lbg.data.emptyResponseBody
import com.lbg.data.emptyVideo
import com.lbg.data.mapper.FilmDetailApiToDomainMapper
import com.lbg.data.mapper.FilmVideoApiToDomainMapper
import com.lbg.data.mockApiVideoResponse
import com.lbg.data.mockVideo
import com.lbg.data.repository.ApiResponseHandler
import com.lbg.domain.shared.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FilmVideoDataSourceImplTest {

    private lateinit var service: FilmService
    private lateinit var filmVideoApiToDomainMapper: FilmVideoApiToDomainMapper
    private lateinit var apiResponseHandler: ApiResponseHandler
    private lateinit var dataSource: FilmVideoDataSource

    @Before
    fun setUp() {
        service = mockk()
        filmVideoApiToDomainMapper = mockk()
        apiResponseHandler = mockk()
        dataSource =
            FilmVideoDataSourceImpl(service, filmVideoApiToDomainMapper, apiResponseHandler)
    }

    @Test
    fun `test getVideo returns success response`() = runTest {
        // Given
        coEvery { service.getMovieVideos(123) } returns Response.success(mockApiVideoResponse())
        coEvery { filmVideoApiToDomainMapper.map(any()) } returns mockVideo()

        // When
        val result = dataSource.getVideo(123).first()

        // Then
        assert(result is Result.Success)
    }

    @Test
    fun `test getVideo returns empty response`() = runTest {
        // Given
        coEvery { service.getMovieVideos(123) } returns Response.success(emptyApiVideoResponse())
        coEvery { filmVideoApiToDomainMapper.map(any()) } returns emptyVideo()

        // When
        val result = dataSource.getVideo(123).first()

        // Then
        assert(result is Result.Success)
    }

    @Test
    fun `test getVideo returns Error response`() = runTest {
        // Given
        coEvery { service.getMovieVideos(321) } returns Response.error(400, emptyResponseBody())

        // When
        val result = dataSource.getVideo(321).first()

        // Then
        assert(result is Result.Error)
    }

    @Test
    fun `test getVideo returns an exception`() = runTest {
        // Given
        coEvery { service.getMovieVideos(987) } throws RuntimeException("Exception")

        // When
        val result = dataSource.getVideo(987).first()

        // Then
        assert(result is Result.Error)
    }

}