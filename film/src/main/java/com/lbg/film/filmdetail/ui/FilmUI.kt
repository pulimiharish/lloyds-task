package com.lbg.film.filmdetail.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lbg.domain.model.Film
import com.lbg.film.R
import com.lbg.film.shared.ui.AppText
import com.lbg.film.shared.ui.FilmImage
import com.lbg.film.theme.FilmTextWhite
import com.lbg.film.theme.GrayOverlay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmUI(film: Film, onBackPressed: () -> Unit, onPlayButtonClicked: (Long) -> Unit) {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = film.title,
                onBackPressed = onBackPressed
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        FilmOverlay(film = film, onPlayButtonClicked = onPlayButtonClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    title: String, onBackPressed: () -> Unit
) {
    TopAppBar(
        title = {
            AppText(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(color = FilmTextWhite),
                modifier = Modifier.padding(end = 8.dp)
            )
        },
        modifier = Modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = FilmTextWhite
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent // Make the app bar background transparent
        ),
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Add scroll behavior for more modern interaction
    )
}

@Composable
private fun FilmOverlay(film: Film, onPlayButtonClicked: (Long) -> Unit) {
    // Box with content alignment and background color
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        FilmImage(
            image = film.image,
            modifier = Modifier.fillMaxSize()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(GrayOverlay.copy(alpha = 0.7f))
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                FilmDetailsText(film = film)
            }
        }

        IconButton(
            onClick = { onPlayButtonClicked(film.id) },
            modifier = Modifier
                .align(Alignment.Center)
                .width(50.dp)
                .height(50.dp)
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(2.dp)
                .clickable { onPlayButtonClicked(film.id) }
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = stringResource(id = R.string.play_video),
                tint = Color.White,
                modifier = Modifier
                    .background(GrayOverlay, shape = MaterialTheme.shapes.large)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun FilmDetailsText(film: Film) {
    // Title text
    AppText(
        text = film.title,
        style = MaterialTheme.typography.headlineMedium.copy(color = FilmTextWhite),
        modifier = Modifier.padding(bottom = 8.dp)
    )

    // Movie rating text
    film.voteAverage?.takeIf { it.isNotEmpty() }?.let {
        AppText(
            text = stringResource(id = R.string.star_rating, it),
            style = MaterialTheme.typography.headlineSmall.copy(color = FilmTextWhite),
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }

    // Description text
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 72.dp)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AppText(
            text = film.overview,
            style = MaterialTheme.typography.bodyLarge.copy(color = FilmTextWhite, fontSize = 16.sp),
            maxLines = 3, // Limit the text to 3 lines
            modifier = Modifier.fillMaxWidth() // Ensure the text takes up the full width of the Box
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}
