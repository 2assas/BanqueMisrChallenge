package banquemisr.challenge05.data.repositories

import banquemisr.challenge05.data.dataSource.local.MovieLocalDataSource
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
    private val localDataSource: MovieLocalDataSource,
    private val movieMapper: MovieListMapper,
    private val movieDetailMapper: MovieDetailMapper
) : MovieRepository {

    override suspend fun fetchMovies(category: MovieCategory, page: Int): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            // First try fetching from the local cache
            val cachedMovies = localDataSource.getMovies(page)
            if (cachedMovies.isNotEmpty()) {
                return@withContext Result.success(cachedMovies)
            }

            // If no cache, fetch from the remote source
            try {
                val response = remoteDataSource.fetchMovies(category.apiPath, page)
                val movies = movieMapper.toDomain(response)
                // Save the fetched movies to the local database
                localDataSource.saveMovies(movies, page)
                Result.success(movies)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun fetchMovieDetails(movieId: Int): Result<MovieDetail?> =
        withContext(Dispatchers.IO) {
            // First try fetching from the local cache
            val cachedMovieDetail = localDataSource.getMovieDetail(movieId)
            cachedMovieDetail?.let {
                return@withContext Result.success(cachedMovieDetail)
            }
            // If no cache, fetch from the remote source
            try {
                val response = remoteDataSource.fetchMovieDetails(movieId)
                val movieDetail = movieDetailMapper.toDomain(response)
                // Save the fetched movie detail to the local database
                localDataSource.saveMovieDetail(movieDetail)
                Result.success(movieDetail)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
