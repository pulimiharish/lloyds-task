package com.lbg.film.films.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.domain.model.Film
import com.lbg.domain.shared.Result
import com.lbg.domain.usecase.FilmsListUseCase
import com.lbg.film.state.FilmsPaginationState
import com.lbg.film.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsListViewModel @Inject constructor(
    private val filmsListUseCase: FilmsListUseCase
) : ViewModel() {

    private val _filmStateFlow: MutableStateFlow<ViewState<List<Film>>> =
        MutableStateFlow(ViewState.Idle)
    val filmStateFlow = _filmStateFlow.asStateFlow()

    private val _paginationState: MutableStateFlow<FilmsPaginationState> =
        MutableStateFlow(FilmsPaginationState())
    val paginationState = _paginationState.asStateFlow()

    init {
        getFilmsList()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getFilmsList() {
         viewModelScope.launch {
             _paginationState.value = _paginationState.value.copy(
                 isLoadingMore = true
             )

            filmsListUseCase(_paginationState.value.currentPage).collect {
                when (it) {
                    is Result.Error -> {
                        _filmStateFlow.value = ViewState.Error(it.throwable.message ?: "error loading")
                        _paginationState.value = _paginationState.value.copy(
                            isLoadingMore = false
                        )
                    }

                    is Result.Success -> {
                        val currentState = _paginationState.value
                        val newFilms = currentState.films + it.data
                        _paginationState.value = _paginationState.value.copy(
                            films = newFilms,
                            currentPage = currentState.currentPage + 1,
                            isLoadingMore = false
                        )
                        _filmStateFlow.value = ViewState.Success(newFilms)
                    }
                }
            }
        }
    }

    // Function to load the next page of films
    fun loadNextPage() {
        getFilmsList()
    }
}