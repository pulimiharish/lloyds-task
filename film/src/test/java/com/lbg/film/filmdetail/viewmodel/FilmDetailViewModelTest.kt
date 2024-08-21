package com.lbg.film.filmdetail.viewmodel

import com.lbg.film.mockFilm
import com.lbg.domain.shared.Result
import com.lbg.domain.usecase.FilmDetailUseCase
import com.lbg.film.state.ViewState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FilmDetailViewModelTest() {

    private val filmDetailUseCase: FilmDetailUseCase = mockk()
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test get film detail method success`() = runTest {
        // Given
        val expectedResult = Result.Success(mockFilm())
        val id = 123L
        coEvery { filmDetailUseCase(id) } returns flowOf(expectedResult)

        // When
        val viewModel = FilmDetailViewModel(filmDetailUseCase)
        viewModel.getFilm(id)

        // Then
        val state = viewModel.filmStateFlow.value
        assert(state is ViewState.Success)
        assertEquals(id, (state as ViewState.Success).data.id)
        assert(state.data.id == id)
    }


    @Test
    fun `test get film detail method failure`() = runTest {
        // Given
        val expectedResult = Result.Error(IllegalStateException("Error"))
        val id = 123L
        coEvery { filmDetailUseCase(id) } returns flowOf(expectedResult)

        // When
        val viewModel = FilmDetailViewModel(filmDetailUseCase)
        viewModel.getFilm(id)

        // Then
        val state = viewModel.filmStateFlow.value
        assert(state is ViewState.Error)
        assertEquals(expectedResult.throwable.message, (state as ViewState.Error).message)
        assert(state.message == "Error")

    }
}