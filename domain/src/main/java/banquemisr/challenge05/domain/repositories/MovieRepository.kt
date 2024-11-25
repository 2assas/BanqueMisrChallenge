package banquemisr.challenge05.domain.repositories

import banquemisr.challenge05.domain.entities.DetailedMovie
import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieCategory

interface MovieRepository {
    suspend fun fetchMovies(category: MovieCategory): List<Movie>
    suspend fun fetchMovieDetails(movieId: Int): DetailedMovie
}