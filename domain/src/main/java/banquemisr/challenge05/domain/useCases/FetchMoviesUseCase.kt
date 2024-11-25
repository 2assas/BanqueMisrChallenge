package banquemisr.challenge05.domain.useCases

import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieCategory
import banquemisr.challenge05.domain.repositories.MovieRepository

class FetchMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(category: MovieCategory): List<Movie> {
        return repository.fetchMovies(category)
            .filter { movie ->
                isValidMovie(movie)
            }
    }

    private fun isValidMovie(movie: Movie): Boolean {
        return movie.id > 0 && !movie.title.isNullOrEmpty()
    }
}
