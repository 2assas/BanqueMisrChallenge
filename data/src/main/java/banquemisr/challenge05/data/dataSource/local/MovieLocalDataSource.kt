package banquemisr.challenge05.data.dataSource.local

import banquemisr.challenge05.data.dataSource.local.dao.MovieDetailDao
import banquemisr.challenge05.data.dataSource.local.dao.MovieListDao
import banquemisr.challenge05.data.dataSource.local.mappers.MovieDetailLocalMapper
import banquemisr.challenge05.data.dataSource.local.mappers.MovieListLocalMapper
import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieDetail
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val movieDatabase: MovieDatabase,
    private val movieListMapper: MovieListLocalMapper,
    private val movieDetailMapper: MovieDetailLocalMapper
) {

    suspend fun saveMovies(movies: List<Movie>, page: Int, category: String) {
        val movieListEntities =
            movies.map { movieListMapper.toEntity(it).copy(page = page, category = category) }
        movieDatabase.movieListDao().insertMovies(movieListEntities)
    }

    suspend fun getMovies(page: Int, category: String): List<Movie> {
        val movieListEntities = movieDatabase.movieListDao().getMoviesByPage(page, category)
        return movieListEntities.map { movieListMapper.toDomain(it) }
    }

    suspend fun getMovieDetail(movieId: Int): MovieDetail? {
        val movieDetailEntity = movieDatabase.movieDetailDao().getMovieDetail(movieId)
        return movieDetailEntity?.let { movieDetailMapper.toDomain(it) }
    }

    suspend fun saveMovieDetail(movie: MovieDetail) {
        val movieDetailEntity = movieDetailMapper.toEntity(movie)
        movieDatabase.movieDetailDao().insertMovieDetail(movieDetailEntity)
    }
}
