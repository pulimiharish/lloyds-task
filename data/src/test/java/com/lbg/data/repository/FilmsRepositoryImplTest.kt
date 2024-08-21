package com.lbg.data.repository

import com.lbg.data.mockFilm1
import com.lbg.data.mockFilmsList
import com.lbg.data.mockVideo
import com.lbg.data.repository.source.FilmDetailsDataSource
import com.lbg.data.repository.source.FilmVideoDataSource
import com.lbg.data.repository.source.FilmsListDataSource
import com.lbg.domain.repository.FilmsRepository
import com.lbg.domain.shared.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilmsRepositoryImplTest {

    private lateinit var filmsListDataSource: FilmsListDataSource
    private lateinit var filmDetailsDataSource: FilmDetailsDataSource
    private lateinit var filmVideoDataSource: FilmVideoDataSource
    private lateinit var filmsRepository: FilmsRepository

    @Before
    fun setup() {
        filmsListDataSource = mockk()
        filmDetailsDataSource = mockk()
        filmVideoDataSource = mockk()
        filmsRepository = FilmsRepositoryImpl(filmsListDataSource, filmDetailsDataSource, filmVideoDataSource)
    }

    @Test
    fun `getFilmsList method returns success`() = runTest {
        // Given
        val expectedResult = Result.Success(mockFilmsList())
        coEvery { filmsListDataSource.getFilmsList() } returns flowOf(expectedResult)

        // When
        val result = filmsRepository.getFilmsList().toList()

        // Then
        coVerify { filmsListDataSource.getFilmsList() }
        assertEquals(1, result.size)
        assertEquals(expectedResult, result.first())
    }

    @Test
    fun `getFilmsList method returns error response`() = runTest {
        // Given
        val expectedResult = Result.Error(IllegalArgumentException("response failed"))
        coEvery { filmsListDataSource.getFilmsList() } returns flowOf(expectedResult)

        // When
        val result = filmsRepository.getFilmsList().toList()

        // Then
        coVerify { filmsListDataSource.getFilmsList() }
        assertEquals(1, result.size)
        assertEquals(expectedResult, result.first())
    }

    @Test
    fun `getFilm returns success response`() = runTest {
        // Given
        val expectedResult = Result.Success(mockFilm1())
        coEvery { filmDetailsDataSource.getFilm(any()) } returns flowOf(expectedResult)

        // When
        val result = filmsRepository.getFilm(123).toList()

        // Then
        coVerify { filmDetailsDataSource.getFilm(123) }
        assertEquals(1, result.size)
        assertEquals(expectedResult, result.first())
    }

    @Test
    fun `getFilm returns error response`() = runTest {
        // Given
        val expectedResult = Result.Error(IllegalArgumentException("response failed"))
        coEvery { filmDetailsDataSource.getFilm(any()) } returns flowOf(expectedResult)

        // When
        val result = filmsRepository.getFilm(123).toList()

        // Then
        coVerify { filmDetailsDataSource.getFilm(123) }
        assertEquals(1, result.size)
        assertEquals(expectedResult, result.first())
    }

    @Test
    fun `getVideo returns success response`() = runTest {
        // Given
        val expectedResult = Result.Success(mockVideo())
        coEvery { filmVideoDataSource.getVideo(any()) } returns flowOf(expectedResult)

        // When
        val result = filmsRepository.getVideo(123).toList()

        // Then
        coVerify { filmVideoDataSource.getVideo(123) }
        assertEquals(1, result.size)
        assertEquals(expectedResult, result.first())
    }

    @Test
    fun `getVideo returns error response`() = runTest {
        // Given
        val expectedResult = Result.Error(IllegalArgumentException("response failed"))
        coEvery { filmVideoDataSource.getVideo(any()) } returns flowOf(expectedResult)

        // When
        val result = filmsRepository.getVideo(123).toList()

        // Then
        coVerify { filmVideoDataSource.getVideo(123) }
        assertEquals(1, result.size)
        assertEquals(expectedResult, result.first())
    }
}