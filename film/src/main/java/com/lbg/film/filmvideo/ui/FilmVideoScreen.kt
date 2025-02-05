package com.lbg.film.filmvideo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.lbg.film.R
import com.lbg.film.filmvideo.viewmodel.FilmVideoViewModel
import com.lbg.film.shared.ui.AppText
import com.lbg.film.shared.ui.ErrorView
import com.lbg.film.shared.ui.LoadingView
import com.lbg.film.state.ViewState
import com.lbg.film.theme.FilmTextWhite
import com.lbg.film.theme.GrayOverlay
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun FilmVideoScreen(
    viewModel: FilmVideoViewModel = hiltViewModel(),
    id: Long
) {

    LaunchedEffect(key1 = true) {
        viewModel.getVideo(id)
    }
    when (val film = viewModel.filmStateFlow.collectAsState().value) {
        is ViewState.Success -> {
            val videoKey = film.data.key

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayOverlay)
            ) {
                if (videoKey.isEmpty()) {
                    AppText(
                        text = stringResource(R.string.this_film_doesn_t_have_any_trailers),
                        style = MaterialTheme.typography.headlineMedium.copy(color = FilmTextWhite),
                        modifier = Modifier
                            .align(Alignment.Center) // Center text both vertically and horizontally
                            .padding(16.dp) // Adjust padding as needed
                    )
                } else {
                    AndroidView(
                        factory = {
                            YouTubePlayerView(it).apply {
                                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {
                                        youTubePlayer.loadVideo(videoKey, 0f)
                                        youTubePlayer.play()
                                    }
                                })
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
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