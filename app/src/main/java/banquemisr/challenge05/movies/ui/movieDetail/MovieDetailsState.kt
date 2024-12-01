package banquemisr.challenge05.movies.ui.movieDetail

import banquemisr.challenge05.domain.entities.MovieDetail

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Success(val movie: MovieDetail) : MovieDetailsState()
    data class Error(val message: String) : MovieDetailsState()
}