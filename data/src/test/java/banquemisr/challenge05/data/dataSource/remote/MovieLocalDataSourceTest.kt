package banquemisr.challenge05.data.dataSource.remote

import banquemisr.challenge05.data.dataSource.local.MovieDatabase
import banquemisr.challenge05.data.dataSource.local.MovieLocalDataSource
import banquemisr.challenge05.data.dataSource.local.dao.MovieListDao
import banquemisr.challenge05.data.dataSource.local.mappers.MovieDetailLocalMapper
import banquemisr.challenge05.data.dataSource.local.mappers.MovieListLocalMapper
import banquemisr.challenge05.domain.entities.Movie
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class MovieLocalDataSourceTest {

    @Mock
    private lateinit var movieDatabase: MovieDatabase

    @Mock
    private lateinit var movieDao: MovieListDao

    @Mock
    private lateinit var movieMapper: MovieListLocalMapper

    @Mock
    private lateinit var movieDetailMapper: MovieDetailLocalMapper

    private lateinit var movieLocalDataSource: MovieLocalDataSource

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        movieMapper = MovieListLocalMapper()
        `when`(movieDatabase.movieListDao()).thenReturn(movieDao)
        movieLocalDataSource = MovieLocalDataSource(movieDatabase, movieMapper, movieDetailMapper)
    }

    @Test
    fun `should save and retrieve movies`() = runTest {
        // Arrange
        val page = 1
        val category = "popular"
        val movies = listOf(
            Movie(id = 1, title = "Inception"),
            Movie(id = 2, title = "Interstellar")
        )

        // Creating MovieEntity objects
        val movieEntities = movies.map { movie ->
            movieMapper.toEntity(movie).copy(page = page)
        }

        // Mock the DAO interactions
        `when`(movieDao.insertMovies(movieEntities)).thenReturn(Unit)
        `when`(movieDao.getMoviesByPage(page, category = category)).thenReturn(movieEntities)

        // Act
        movieLocalDataSource.saveMovies(movies, page, category)

        val result = movieLocalDataSource.getMovies(page, category)

        // Assert
        assertEquals(movies, result)

        // Verify the interactions with the DAO
        verify(movieDao).insertMovies(movieEntities)
        verify(movieDao).getMoviesByPage(page, category)
    }

    @Test
    fun `should return empty list if no movies are cached`() = runTest {
        val movieDao = movieDatabase.movieListDao()
        val category = "popular"

        // Arrange: Mock the response from DAO to return an empty list
        `when`(movieDao.getMoviesByPage(1, category)).thenReturn(emptyList())

        // Act: Fetch movies from the local data source
        val result = movieLocalDataSource.getMovies(1, category = category)

        // Assert: Ensure that the result is an empty list
        assertTrue(result.isEmpty())
    }

}
