package com.lbg.film.filmdetail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.lbg.film.filmdetail.viewmodel.FilmDetailViewModel
import com.lbg.film.shared.ui.ErrorView
import com.lbg.film.shared.ui.LoadingView
import com.lbg.film.state.ViewState

@Composable
fun FilmDetailScreen(
    viewModel: FilmDetailViewModel = hiltViewModel(),
    id: Long,
    selectedItem: (Long) -> Unit,
    onBackPressed: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.getFilm(id)
    }
    when (val film = viewModel.filmStateFlow.collectAsState().value) {
        is ViewState.Success -> {
            FilmUI(film = film.data, onBackPressed = onBackPressed, onPlayButtonClicked = selectedItem)
        }
        is ViewState.Loading -> {
            LoadingView()
        }
        is ViewState.Error -> {
            ErrorView(reason = film.message)
        }
        else -> {}
    }
}