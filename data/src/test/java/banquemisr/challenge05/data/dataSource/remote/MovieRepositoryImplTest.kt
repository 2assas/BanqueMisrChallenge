package banquemisr.challenge05.data.dataSource.remote

import banquemisr.challenge05.data.dataSource.local.MovieLocalDataSource
import banquemisr.challenge05.data.mappers.MovieDetailMapper
import banquemisr.challenge05.data.mappers.MovieListMapper
import banquemisr.challenge05.data.models.MovieResponse
import banquemisr.challenge05.data.repositories.MovieRepositoryImpl
import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieCategory
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MovieRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: MovieRemoteDataSource

    @Mock
    private lateinit var localDataSource: MovieLocalDataSource

    @Mock
    private lateinit var movieMapper: MovieListMapper

    @Mock
    private lateinit var movieDetailMapper: MovieDetailMapper

    private lateinit var movieRepository: MovieRepositoryImpl

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        movieRepository =
            MovieRepositoryImpl(remoteDataSource, localDataSource, movieMapper, movieDetailMapper)
    }

    @Test
    fun `should fetch movies from cache if available`() = runTest {
        // Arrange
        val cachedMovies = listOf(Movie(1, "Cached Title"))
        `when`(localDataSource.getMovies(1)).thenReturn(cachedMovies)

        // Act
        val result = movieRepository.fetchMovies(MovieCategory.UPCOMING, 1)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(cachedMovies, result.getOrNull())
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `should fetch movies from remote when cache is empty`() = runTest {
        // Arrange
        val category = MovieCategory.UPCOMING
        val page = 1
        val remoteMoviesDto = MovieResponse(
            movieDtoList = listOf(
                MovieResponse.MovieDto(
                    id = 1,
                    title = "Remote Title",
                    releaseDate = "2023-01-01"
                )
            )
        )
        val mappedMovies = listOf(Movie(1, "Remote Title"))
        `when`(localDataSource.getMovies(page)).thenReturn(emptyList())
        `when`(
            remoteDataSource.fetchMovies(
                category.apiPath,
                page
            )
        ).thenReturn(remoteMoviesDto)

        `when`(movieMapper.toDomain(remoteMoviesDto)).thenReturn(mappedMovies)

        // Act
        val result = movieRepository.fetchMovies(category, page)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(mappedMovies, result.getOrNull())
        verify(localDataSource).saveMovies(mappedMovies, page)
    }
}
