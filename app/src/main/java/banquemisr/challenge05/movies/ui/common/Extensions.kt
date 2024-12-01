package banquemisr.challenge05.movies.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import banquemisr.challenge05.domain.entities.MovieCategory
import banquemisr.challenge05.movies.ui.movieList.ListScreenIntent
import com.google.accompanist.systemuicontroller.rememberSystemUiController


fun Int.getListScreenIntentByTabIndex(): ListScreenIntent {
    return when (this) {
        0 -> ListScreenIntent.LoadNowPlayingMovies
        1 -> ListScreenIntent.LoadPopularMovies
        else -> ListScreenIntent.LoadUpcomingMovies
    }
}
fun Int.getMovieCategoryByTabIndex(): MovieCategory {
    return when (this) {
        0 -> MovieCategory.NOW_PLAYING
        1 -> MovieCategory.POPULAR
        else -> MovieCategory.UPCOMING
    }
}

@Composable
fun ChangeStatusBarColor(color: Color) {
    // Get the System UI Controller
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false

    // Change the status bar color
    systemUiController.setSystemBarsColor(
        color = color,
        darkIcons = useDarkIcons
    )
}
