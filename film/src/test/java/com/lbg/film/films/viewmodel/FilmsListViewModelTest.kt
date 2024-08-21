package com.lbg.film.films.viewmodel

import com.lbg.domain.shared.Result
import com.lbg.domain.usecase.FilmsListUseCase
import com.lbg.film.mockFilmsList
import com.lbg.film.mocknextFilmsList
import com.lbg.film.state.ViewState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FilmsListViewModelTest {

    private val filmsListUseCase: FilmsListUseCase = mockk()
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
    fun `test get films list method failure`() = runTest {
        // Given
        val expectedError = Result.Error(IllegalArgumentException("some error"))
        coEvery { filmsListUseCase() } returns flowOf(expectedError)

        // When
        val viewModel = FilmsListViewModel(filmsListUseCase)
        viewModel.getFilmsList()

        // Then
        val result = viewModel.filmStateFlow.value
        assert(result is ViewState.Error)
        assertEquals(expectedError.throwable.message, (result as ViewState.Error).message)
        assert(result.message == "some error")
    }

    @Test
    fun `test get films list method success`() = runTest {
        // Given
        val mockFilms = mockFilmsList()
        val expectedResult = Result.Success(mockFilms)
        coEvery { filmsListUseCase(any()) } returns flowOf(expectedResult)

        // When
        val viewModel = FilmsListViewModel(filmsListUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.filmStateFlow.value
        assert(state is ViewState.Success) { "Expected ViewState.Success but got ${state::class.simpleName}" }
        assertEquals(expectedResult.data, (state as ViewState.Success).data)
        assert(state.data.size == mockFilms.size) { "Expected size ${mockFilms.size} but got ${state.data.size}" }
    }

    @Test
    fun `test loadNextPage method success`() = runTest {
        // Given
        val initialFilms = mockFilmsList()
        val nextFilms = mocknextFilmsList()
        val expectedInitialResult = Result.Success(initialFilms)
        val expectedNextResult = Result.Success(nextFilms)

        coEvery { filmsListUseCase(1) } returns flowOf(expectedInitialResult)
        coEvery { filmsListUseCase(2) } returns flowOf(expectedNextResult)

        // When
        val viewModel = FilmsListViewModel(filmsListUseCase)
        advanceUntilIdle()
        viewModel.loadNextPage()
        advanceUntilIdle()

        // Then
        val state = viewModel.filmStateFlow.value
        assert(state is ViewState.Success) { "Expected ViewState.Success but got ${state::class.simpleName}" }
        val data = (state as ViewState.Success).data
        assert(data.containsAll(initialFilms)) { "Data should contain all initial films" }
        assert(data.containsAll(nextFilms)) { "Data should contain all next films" }
    }

    @Test
    fun `test loadNextPage method failure`() = runTest {
        // Given
        val initialFilms = mockFilmsList()
        val expectedInitialResult = Result.Success(initialFilms)
        val expectedError = Result.Error(IllegalArgumentException("Pagination failed"))

        coEvery { filmsListUseCase(1) } returns flowOf(expectedInitialResult)
        coEvery { filmsListUseCase(2) } returns flowOf(expectedError)

        // When
        val viewModel = FilmsListViewModel(filmsListUseCase)
        advanceUntilIdle()
        viewModel.loadNextPage()
        advanceUntilIdle()

        // Then
        val state = viewModel.filmStateFlow.value
        assert(state is ViewState.Error)
        assert(viewModel.isLoadingMore.not())
    }
}