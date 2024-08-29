package com.lbg.film.films.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.domain.model.Film
import com.lbg.domain.shared.Result
import com.lbg.domain.usecase.FilmsListUseCase
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

    // Pagination state
    private var currentPage = 1
    var isLoadingMore = false
    private var allFilms: List<Film> = emptyList()

    init {
        getFilmsList(currentPage)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getFilmsList(page: Int = 1) {
         viewModelScope.launch {
            isLoadingMore = true

            filmsListUseCase(page).collect {
                when (it) {
                    is Result.Error -> {
                        _filmStateFlow.value = ViewState.Error(it.throwable.message ?: "error loading")
                        isLoadingMore = false
                    }

                    is Result.Success -> {
                        val newList = allFilms + it.data
                        _filmStateFlow.value = ViewState.Success(newList)
                        allFilms = newList
                        currentPage++
                        isLoadingMore = false
                    }
                }
            }
        }
    }

    // Function to load the next page of films
    fun loadNextPage() {
        getFilmsList(currentPage)
    }
}