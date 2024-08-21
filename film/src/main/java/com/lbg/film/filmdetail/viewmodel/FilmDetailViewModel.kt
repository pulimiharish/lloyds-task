package com.lbg.film.filmdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.domain.model.Film
import com.lbg.domain.shared.Result
import com.lbg.domain.usecase.FilmDetailUseCase
import com.lbg.film.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    private val filmDetailUseCase: FilmDetailUseCase
) : ViewModel() {

    private val _filmStateFlow: MutableStateFlow<ViewState<Film>> =
        MutableStateFlow(ViewState.Idle)
    val filmStateFlow = _filmStateFlow.asStateFlow()

    fun getFilm(id: Long) {
        viewModelScope.launch {
            _filmStateFlow.value = ViewState.Loading
            filmDetailUseCase(id).collect {
                when (it) {
                    is Result.Error -> {
                        _filmStateFlow.value =
                            ViewState.Error(it.throwable.message ?: "error loading")
                    }

                    is Result.Success -> {
                        _filmStateFlow.value = ViewState.Success(it.data)
                    }
                }
            }
        }
    }
}