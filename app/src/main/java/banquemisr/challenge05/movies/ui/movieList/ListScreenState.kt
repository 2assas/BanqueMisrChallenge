package banquemisr.challenge05.movies.ui.movieList

import banquemisr.challenge05.domain.entities.Movie

sealed class ListScreenState {
    data object Loading : ListScreenState()
    data class Success(
        val movies: List<Movie>,
        val isLoadingNextPage: Boolean
    ) : ListScreenState()
    data class Error(val message: String) : ListScreenState()
}