package banquemisr.challenge05.movies.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import banquemisr.challenge05.movies.ui.movieDetail.MovieDetailsScreen
import banquemisr.challenge05.movies.ui.movieList.MovieTabsScreen

fun NavController.navigateToMovieTabsScreen() {
    this.navigate(Destinations.MOVIE_TABS_SCREEN)
}

fun NavController.navigateToMovieDetailScreen(movieId: Int) {
    this.navigate("movieDetailScreen/$movieId")
}

fun NavGraphBuilder.movieTabsScreen(navController: NavController) {
    composable(Destinations.MOVIE_TABS_SCREEN) {
        MovieTabsScreen(navController = navController)
    }
}

fun NavGraphBuilder.movieDetailScreen(navController: NavController) {
    composable("movieDetailScreen/{movieId}") { backStackEntry ->
        val movieId = backStackEntry.arguments?.getString("movieId")
        movieId?.let {
            MovieDetailsScreen(movieId = movieId.toInt(), navController = navController)
        }
    }
}
