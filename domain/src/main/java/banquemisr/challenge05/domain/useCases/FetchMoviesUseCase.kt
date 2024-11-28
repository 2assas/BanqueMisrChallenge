package banquemisr.challenge05.domain.useCases

import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieCategory
import banquemisr.challenge05.domain.repositories.MovieRepository

class FetchMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(category: MovieCategory, page: Int): Result<List<Movie>?> {
        if (page < 1) throw IllegalArgumentException("Page must be greater than 0")
        return repository.fetchMovies(category, page).map { movie ->
            movie?.filter { isValidMovie(it) }
        }
    }

    private fun isValidMovie(movie: Movie): Boolean {
        return movie.id > 0 && !movie.title.isNullOrEmpty()
    }
}
