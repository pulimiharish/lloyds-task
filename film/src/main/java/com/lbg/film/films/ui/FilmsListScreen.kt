package com.lbg.film.films.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lbg.film.films.viewmodel.FilmsListViewModel
import com.lbg.film.shared.ui.ErrorView
import com.lbg.film.shared.ui.LoadingView
import com.lbg.film.state.ViewState
import com.lbg.film.theme.GrayOverlay

@Composable
fun FilmsListScreen(
    viewModel: FilmsListViewModel = hiltViewModel(),
    selectedItem: (Long) -> Unit
) {
    val filmState = viewModel.filmStateFlow.collectAsState().value
    val paginationState = viewModel.paginationState.collectAsState().value
    val gridState = rememberLazyGridState()

    when (filmState) {
        is ViewState.Success -> {
            val list = filmState.data
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayOverlay)
                    .padding(8.dp),
                state = gridState
            ) {
                items(list, key = { it.id }) { film ->
                    FilmGridCardUI(film, selectedItem)
                }

                if (paginationState.isLoadingMore) {
                    item {
                        LoadingView()
                    }
                } else {
                    item {
                        viewModel.loadNextPage()
                    }
                }
            }
        }

        is ViewState.Error -> {
            ErrorView(reason = filmState.message)
        }

        else -> {}
    }
}
