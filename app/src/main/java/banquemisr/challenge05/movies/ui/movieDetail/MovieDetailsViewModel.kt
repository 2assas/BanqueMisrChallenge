package banquemisr.challenge05.movies.ui.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.domain.useCases.FetchMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor (
    private val fetchMovieDetailsUseCase: FetchMovieDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val state: StateFlow<MovieDetailsState> get() = _state

    fun handleIntent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.LoadMovieDetails -> loadMovieDetails(intent.movieId)
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _state.value = MovieDetailsState.Loading
            try {

                val result = fetchMovieDetailsUseCase(movieId)
                result.onSuccess { movieDetail ->
                    if (movieDetail == null) _state.value =
                        MovieDetailsState.Error("Movie not found")
                    else
                        _state.value = MovieDetailsState.Success(movieDetail)
                }.onFailure { exception ->
                    _state.value =
                        MovieDetailsState.Error(exception.message ?: "An unexpected error occurred")
                }
            } catch (e: Exception) {
                _state.value = MovieDetailsState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}
