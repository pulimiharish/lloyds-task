package com.lbg.domain.usecase

import com.lbg.domain.mockFilmsList
import com.lbg.domain.model.Film
import com.lbg.domain.repository.FilmsRepository
import com.lbg.domain.shared.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilmsListUseCaseTest {

    private val filmsRepository = mockk<FilmsRepository>()
    private lateinit var filmsListUseCase: FilmsListUseCase

    @Before
    fun setUp() {
        filmsListUseCase = FilmsListUseCase(filmsRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test usecase invoke with Success Response`() = runTest {
        // Given
        val expectedResult = Result.Success(mockFilmsList())
        coEvery { filmsRepository.getFilmsList() } returns flowOf(expectedResult)

        // When
        val result = mutableListOf<Result<List<Film>>>()
        filmsListUseCase().collect {
            result.add(it)
        }

        // Then
        assertEquals(listOf(expectedResult), result)
    }

    @Test
    fun `test usecase invoke with Error Response`() = runTest {

        // Given
        val expectedResult = Result.Error(IllegalArgumentException("response failed"))
        coEvery { filmsRepository.getFilmsList() } returns flowOf(expectedResult)

        // When
        val result = mutableListOf<Result<List<Film>>>()
        filmsListUseCase().collect {
            result.add(it)
        }

        // Then
        assertEquals(listOf(expectedResult), result)
    }

}