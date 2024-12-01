package banquemisr.challenge05.movies.ui.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieCategory
import banquemisr.challenge05.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ListScreenState>(ListScreenState.Loading)
    val state = _state
    var selectedTabIndex = 0
    private var currentPage = 1
    private var isLastPage = false
    private var currentMovies = mutableListOf<Movie>()

    fun handleEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.LoadNowPlayingMovies -> loadMovies(MovieCategory.NOW_PLAYING)
            is ListScreenEvent.LoadPopularMovies -> loadMovies(MovieCategory.POPULAR)
            is ListScreenEvent.LoadUpcomingMovies -> loadMovies(MovieCategory.UPCOMING)
            is ListScreenEvent.LoadNextPage -> loadNextPage()
        }
    }

    init {
        loadMovies(MovieCategory.NOW_PLAYING)
    }

    private fun loadMovies(category: MovieCategory) {
        viewModelScope.launch {
            currentMovies = mutableListOf()
            _state.emit(ListScreenState.Loading)
            try {
                // Call repository and handle the Result class
                val result = movieRepository.fetchMovies(category, page = 1)

                // Handle success and failure cases
                when {
                    result.isSuccess -> {
                        currentMovies = result.getOrNull()?.toMutableList() ?: mutableListOf()
                        currentPage = 1
                        isLastPage = result.getOrNull()?.isEmpty() ?: true
                        _state.emit(
                            ListScreenState.Success(
                                currentMovies,
                                isLoadingNextPage = false
                            )
                        )
                    }

                    result.isFailure -> {
                        _state.emit(ListScreenState.Error("Failed to load movies: ${result.exceptionOrNull()?.message ?: "Unknown error"}"))
                    }
                }
            } catch (e: Exception) {
                _state.emit(ListScreenState.Error("Failed to load movies"))
            }
        }
    }


    private fun loadNextPage() {
        if (isLastPage || _state.value is ListScreenState.Loading) return

        viewModelScope.launch {
            _state.emit(ListScreenState.Success(currentMovies, isLoadingNextPage = true))
            try {
                // Call repository and handle the Result class for next page
                val result = movieRepository.fetchMovies(
                    category = MovieCategory.NOW_PLAYING, // Replace with dynamic category
                    page = currentPage + 1
                )

                when {
                    result.isSuccess -> {
                        val nextPageMovies = result.getOrNull() ?: emptyList()
                        if (nextPageMovies.isNotEmpty()) {
                            currentMovies.addAll(nextPageMovies)
                            currentPage++
                        } else {
                            isLastPage = true
                        }
                        _state.emit(
                            ListScreenState.Success(
                                currentMovies,
                                isLoadingNextPage = false
                            )
                        )
                    }

                    result.isFailure -> {
                        _state.emit(ListScreenState.Error("Failed to load more movies: ${result.exceptionOrNull()?.message ?: "Unknown error"}"))
                    }
                }
            } catch (e: Exception) {
                _state.emit(ListScreenState.Error("Failed to load more movies"))
            }
        }
    }
}
