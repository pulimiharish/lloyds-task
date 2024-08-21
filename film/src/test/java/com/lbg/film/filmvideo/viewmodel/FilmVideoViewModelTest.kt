package com.lbg.film.filmvideo.viewmodel

import com.lbg.film.mockFilm
import com.lbg.domain.shared.Result
import com.lbg.domain.usecase.FilmVideoUseCase
import com.lbg.film.filmdetail.viewmodel.FilmDetailViewModel
import com.lbg.film.filmdetail.viewmodel.FilmVideoViewModel
import com.lbg.film.mockVideo
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
class FilmVideoViewModelTest() {

    private val filmVideoUseCase: FilmVideoUseCase = mockk()
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
    fun `test get film video method success`() = runTest {
        // Given
        val expectedResult = Result.Success(mockVideo())
        val id = 123
        val key = "12345"
        coEvery { filmVideoUseCase(id) } returns flowOf(expectedResult)

        // When
        val viewModel = FilmVideoViewModel(filmVideoUseCase)
        viewModel.getVideo(123L)

        // Then
        val state = viewModel.filmStateFlow.value
        assert(state is ViewState.Success)
        assertEquals(key, (state as ViewState.Success).data.key)
    }


    @Test
    fun `test get film video method failure`() = runTest {
        // Given
        val expectedResult = Result.Error(IllegalStateException("Error"))
        val id = 123
        coEvery { filmVideoUseCase(id) } returns flowOf(expectedResult)

        // When
        val viewModel = FilmVideoViewModel(filmVideoUseCase)
        viewModel.getVideo(123L)

        // Then
        val state = viewModel.filmStateFlow.value
        assert(state is ViewState.Error)
        assertEquals(expectedResult.throwable.message, (state as ViewState.Error).message)
        assert(state.message == "Error")

    }
}