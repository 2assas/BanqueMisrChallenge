package banquemisr.challenge05.domain.useCases

import banquemisr.challenge05.domain.entities.MovieDetail
import banquemisr.challenge05.domain.repositories.MovieRepository

class FetchMovieDetailsUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieDetail?> {
        return repository.fetchMovieDetails(movieId)
    }
}