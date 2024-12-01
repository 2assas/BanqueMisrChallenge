package banquemisr.challenge05.movies.ui.movieList

sealed class ListScreenIntent {
    data object LoadNowPlayingMovies : ListScreenIntent()
    data object LoadPopularMovies : ListScreenIntent()
    data object LoadUpcomingMovies : ListScreenIntent()
    data object ClearMovies : ListScreenIntent()
    data object LoadNextPage : ListScreenIntent()
}
