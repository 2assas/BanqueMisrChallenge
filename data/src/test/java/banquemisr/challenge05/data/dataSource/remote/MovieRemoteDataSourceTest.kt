package banquemisr.challenge05.data.dataSource.remote

import banquemisr.challenge05.data.models.MovieDetailResponse
import banquemisr.challenge05.data.models.MovieResponse
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class MovieRemoteDataSourceTest {
    @Mock
    lateinit var apiService: MovieApiService

    @InjectMocks
    lateinit var movieRemoteDataSource: MovieRemoteDataSource

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieRemoteDataSource = MovieRemoteDataSource(apiService)
    }

    @Test
    fun `should fetch movie list successfully`() = runTest {
        // Arrange
        val category = "upcoming"
        val page = 1
        val response = MovieResponse(
            movieDtoList = listOf(
                MovieResponse.MovieDto(
                    id = 1,
                    title = "Title",
                    releaseDate = "2024-01-01",
                    posterPath = "path",
                    voteAverage = 8.0
                )
            )
        )

        `when`(apiService.getMovies(category, page)).thenReturn(response)

        // Act
        val result = movieRemoteDataSource.fetchMovies(category, page)

        // Assert
        assertNotNull(result)
        assertEquals(1, result.movieDtoList?.size)
        assertEquals("Title", result.movieDtoList?.first()?.title)
        verify(apiService).getMovies(category, page)
    }

    @Test
    fun `should handle empty movie list`() = runTest {
        // Arrange
        val category = "upcoming"
        val page = 1
        val response = MovieResponse(movieDtoList = emptyList())

        `when`(apiService.getMovies(category, page)).thenReturn(response)

        // Act
        val result = movieRemoteDataSource.fetchMovies(category, page)

        // Assert
        assertNotNull(result)
        assertTrue(result.movieDtoList.isNullOrEmpty())
        verify(apiService).getMovies(category, page)
    }

    @Test
    fun `should handle network error when fetching movies`() = runTest {
        // Arrange
        val category = "upcoming"
        val page = 1
        `when`(apiService.getMovies(category, page)).thenThrow(RuntimeException("Network Error"))

        // Act
        val result = runCatching {
            movieRemoteDataSource.fetchMovies(category, page)
        }

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `should fetch movie details successfully`() = runTest {
        // Arrange
        val movieId = 1
        val response = MovieDetailResponse(
            id = movieId,
            title = "Title",
            releaseDate = "2024-01-01",
            posterPath = "path",
            overview = "Overview",
            runtime = 120,
            voteAverage = 7.8,
            voteCount = 100,
            originalLanguage = "English"
        )

        `when`(apiService.getMovieDetails(movieId)).thenReturn(response)

        // Act
        val result = movieRemoteDataSource.fetchMovieDetails(movieId)

        // Assert
        assertNotNull(result)
        assertEquals(movieId, result.id)
        assertEquals("Title", result.title)
        verify(apiService).getMovieDetails(movieId)
    }

    @Test
    fun `should handle error when fetching movie details`() = runTest {
        // Arrange
        val movieId = 1
        `when`(apiService.getMovieDetails(movieId)).thenThrow(RuntimeException("Movie Not Found"))

        // Act
        val result = runCatching {
            movieRemoteDataSource.fetchMovieDetails(movieId)
        }

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Movie Not Found", result.exceptionOrNull()?.message)
    }
}