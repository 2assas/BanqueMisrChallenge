package banquemisr.challenge05.data.dataSource.remote

import banquemisr.challenge05.data.models.MovieDetailResponse
import banquemisr.challenge05.data.models.MovieResponse
import banquemisr.challenge05.data.utils.safeApiCall
import javax.inject.Inject


class MovieRemoteDataSource @Inject constructor(
    private val movieApiService: MovieApiService
) {
    suspend fun fetchMovies(category: String, page: Int): MovieResponse {
        return safeApiCall { movieApiService.getMovies(category, page) }
    }

    suspend fun fetchMovieDetails(movieId: Int): MovieDetailResponse {
        return safeApiCall {movieApiService.getMovieDetails(movieId) }
    }
}