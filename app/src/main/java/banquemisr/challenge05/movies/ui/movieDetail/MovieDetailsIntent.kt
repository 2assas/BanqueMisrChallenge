package banquemisr.challenge05.movies.ui.movieDetail

sealed class MovieDetailsIntent {
    data class LoadMovieDetails(val movieId: Int) : MovieDetailsIntent()
}