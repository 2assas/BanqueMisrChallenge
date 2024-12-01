package banquemisr.challenge05.movies.ui.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieCategory
import banquemisr.challenge05.domain.useCases.FetchMoviesUseCase
import banquemisr.challenge05.movies.ui.common.getMovieCategoryByTabIndex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ListScreenState>(ListScreenState.Loading)
    val state = _state
    var selectedTabIndex = 0
    private var currentPage = 1
    private var isLastPage = false
    private var currentMovies = mutableListOf<Movie>()

    fun handleIntent(intent: ListScreenIntent) {
        when (intent) {
            is ListScreenIntent.LoadNowPlayingMovies -> loadMovies(MovieCategory.NOW_PLAYING)
            is ListScreenIntent.LoadPopularMovies -> loadMovies(MovieCategory.POPULAR)
            is ListScreenIntent.LoadUpcomingMovies -> loadMovies(MovieCategory.UPCOMING)
            is ListScreenIntent.LoadNextPage -> loadNextPage(selectedTabIndex.getMovieCategoryByTabIndex())
            is ListScreenIntent.ClearMovies -> {
                _state.value = ListScreenState.Success(emptyList(), isLoadingNextPage = false)
            }
        }
    }

    init {
        loadMovies(MovieCategory.NOW_PLAYING)
    }

    private fun loadMovies(category: MovieCategory) {
        viewModelScope.launch {
            currentMovies = mutableListOf()
            currentPage = 1
            _state.emit(ListScreenState.Loading)
            try {
                // Call repository and handle the Result class
                val result = fetchMoviesUseCase.invoke(category, page = 1)

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


    private fun loadNextPage(category: MovieCategory) {
        if (isLastPage || _state.value is ListScreenState.Loading) return
        viewModelScope.launch {
            _state.emit(ListScreenState.Success(currentMovies, isLoadingNextPage = true))
            try {
                // Call repository and handle the Result class for next page
                val result = fetchMoviesUseCase.invoke(
                    category = category, // Replace with dynamic category
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
