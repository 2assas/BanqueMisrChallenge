package banquemisr.challenge05.movies.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import banquemisr.challenge05.movies.ui.movieList.ListScreenEvent
import com.google.accompanist.systemuicontroller.rememberSystemUiController


fun Int.getListScreenEventByTabIndex(): ListScreenEvent {
    return when (this) {
        0 -> ListScreenEvent.LoadNowPlayingMovies
        1 -> ListScreenEvent.LoadPopularMovies
        else -> ListScreenEvent.LoadUpcomingMovies
    }
}

@Composable
fun ChangeStatusBarColor() {
    // Get the System UI Controller
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false

    // Change the status bar color
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.primary,
        darkIcons = useDarkIcons
    )
}
