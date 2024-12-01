package banquemisr.challenge05.movies.ui.movieDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import banquemisr.challenge05.movies.ui.common.ChangeStatusBarColor
import banquemisr.challenge05.movies.ui.movieDetail.components.MovieDetailsContent

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    navController: NavController
) {
    ChangeStatusBarColor(Color.Transparent)

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(movieId))
    }

    when (state) {
        is MovieDetailsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MovieDetailsState.Success -> {
            val movie = (state as MovieDetailsState.Success).movie
            MovieDetailsContent(movie = movie)
        }

        is MovieDetailsState.Error -> {
            val message = (state as MovieDetailsState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = message, color = Color.Red)
            }
        }
    }
}

