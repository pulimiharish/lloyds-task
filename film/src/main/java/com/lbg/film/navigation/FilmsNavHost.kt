package com.lbg.film.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.lbg.film.filmdetail.ui.FilmDetailScreen
import com.lbg.film.filmvideo.ui.FilmVideoScreen
import com.lbg.film.films.ui.FilmsListScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FilmsNavHost() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = FilmDestinations.FILMS_LIST_ROUTE
    ) {
        composable(
            route = FilmDestinations.FILMS_LIST_ROUTE,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -it }) + fadeIn(initialAlpha = 0.3f)
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { it }) + fadeOut(targetAlpha = 0.3f)
            }
        ) {
            FilmsListScreen { filmId ->
                navController.navigate(filmDetailsRoute(filmId))
            }
        }

        composable(
            route = FilmDestinations.FILM_DETAILS_ROUTE,
            arguments = listOf(navArgument(FilmDestinations.FilmDetailsArgs.FILM_ID) {
                type = NavType.LongType
            }),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }) + fadeIn(initialAlpha = 0.3f)
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(targetAlpha = 0.3f)
            }
        ) { backStackEntry ->
            val filmId = backStackEntry.arguments?.getLong(FilmDestinations.FilmDetailsArgs.FILM_ID)
            filmId?.let { id ->
                FilmDetailScreen(
                    id = id,
                    selectedItem = { selectedFilmId ->
                        navController.navigate(filmVideoRoute(selectedFilmId))
                    },
                    onBackPressed = {
                        navController.navigateUp()
                    }
                )
            }
        }

        composable(
            route = FilmDestinations.FILM_VIDEO_ROUTE,
            arguments = listOf(navArgument(FilmDestinations.FilmDetailsArgs.FILM_ID) {
                type = NavType.LongType
            }),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }) + fadeIn(initialAlpha = 0.3f)
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(targetAlpha = 0.3f)
            }
        ) { backStackEntry ->
            val filmId = backStackEntry.arguments?.getLong(FilmDestinations.FilmDetailsArgs.FILM_ID)
            filmId?.let { id ->
                FilmVideoScreen(
                    id = id
                )
            }
        }
    }
}
