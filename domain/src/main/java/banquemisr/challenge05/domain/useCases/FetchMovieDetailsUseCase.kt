package banquemisr.challenge05.domain.useCases

import banquemisr.challenge05.domain.entities.DetailedMovie
import banquemisr.challenge05.domain.repositories.MovieRepository

class FetchMovieDetailsUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): DetailedMovie {
        return repository.getMovieDetails(movieId)
    }
}