package banquemisr.challenge05.movies.ui.movieList

sealed class ListScreenEvent {
    data object LoadNowPlayingMovies : ListScreenEvent()
    data object LoadPopularMovies : ListScreenEvent()
    data object LoadUpcomingMovies : ListScreenEvent()
    data object LoadNextPage : ListScreenEvent()
}
