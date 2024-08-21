package com.lbg.film.shared.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.lbg.film.R

@Composable
fun FilmImage(
    image: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = image)
            .crossfade(500)
            .placeholder(R.drawable.placeholder_img)
            .error(R.drawable.placeholder_img)
            .build()
    )

    Image(
        painter = painter,
        contentDescription = stringResource(R.string.movie_screenshot),
        contentScale = contentScale,
        modifier = modifier.fillMaxSize(),
    )
}