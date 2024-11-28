package banquemisr.challenge05.data.repositories

import banquemisr.challenge05.data.dataSource.remote.MovieRemoteDataSource
import banquemisr.challenge05.data.mappers.MovieDetailMapper
import banquemisr.challenge05.data.mappers.MovieListMapper
import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieCategory
import banquemisr.challenge05.domain.entities.MovieDetail
import banquemisr.challenge05.domain.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieListMapper,
    private val movieDetailMapper: MovieDetailMapper
) : MovieRepository {

    override suspend fun fetchMovies(category: MovieCategory, page: Int): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.fetchMovies(category.apiPath, page)
                Result.success(movieMapper.toDomain(response))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun fetchMovieDetails(movieId: Int): Result<MovieDetail?> =
        withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.fetchMovieDetails(movieId)
                Result.success(movieDetailMapper.toDomain(response))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
