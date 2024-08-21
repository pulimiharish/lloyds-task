package com.lbg.domain.usecase

import com.lbg.domain.mockFilm
import com.lbg.domain.model.Film
import com.lbg.domain.repository.FilmsRepository
import com.lbg.domain.shared.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilmDetailUseCaseTest {

    private val filmsRepository = mockk<FilmsRepository>()
    private lateinit var filmDetailUseCase: FilmDetailUseCase

    @Before
    fun setUp() {
        filmDetailUseCase = FilmDetailUseCase(filmsRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test usecase invoke with Success Response`() = runTest {
        // Given
        val filmId = 123L

        val expectedResult = Result.Success(mockFilm())
        coEvery { filmsRepository.getFilm(filmId) } returns flow { emit(expectedResult) }

        // When
        val result = mutableListOf<Result<Film>>()
        filmDetailUseCase.invoke(filmId).collect {
            result.add(it)
        }

        // Then
        assertEquals(listOf(expectedResult), result)
    }

    @Test
    fun `test usecase invoke with Error Response`() = runTest {
        // Given
        val filmId = 987L

        val expectedResult = Result.Error(IllegalArgumentException("response failed"))
        coEvery { filmsRepository.getFilm(filmId) } returns flow { emit(expectedResult) }

        // When
        val result = mutableListOf<Result<Film>>()
        filmDetailUseCase.invoke(filmId).collect {
            result.add(it)
        }

        // Then
        assertEquals(listOf(expectedResult), result)
    }
}