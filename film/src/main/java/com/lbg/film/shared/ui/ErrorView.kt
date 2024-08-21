package com.lbg.film.shared.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lbg.film.R

@Composable
fun ErrorView(reason: String, modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.unable_to_load_movies_reason, reason), modifier = modifier.fillMaxSize())
}