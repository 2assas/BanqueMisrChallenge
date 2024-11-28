package banquemisr.challenge05.domain.useCases

import banquemisr.challenge05.domain.entities.MovieDetail
import banquemisr.challenge05.domain.repositories.MovieRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import kotlin.test.assertIs
import kotlin.test.assertTrue

class FetchMovieDetailsUseCaseTest {

    private val repository: MovieRepository = mock(MovieRepository::class.java)
    private val fetchMovieDetailsUseCase = FetchMovieDetailsUseCase(repository)

    @Test
    fun `invoke should return Result-Success with movie details when repository fetches successfully`() = runTest {
        // Arrange
        val movieId = 1
        val movieDetails = MovieDetail(
            id = movieId,
            title = "Movie 1",
            releaseDate = "2024-10-16",
            overview = "A great movie",
            genres = listOf("Action", "Adventure"),
            runtime = 120,
            posterPath = "path1",
            voteAverage = 7.3,
            voteCount = 1002,
            language = "English",
            rating = 3.4
        )
        `when`(repository.fetchMovieDetails(movieId)).thenReturn(Result.success(movieDetails))

        // Act
        val result = fetchMovieDetailsUseCase(movieId)

        // Assert
        assertIs<Result<MovieDetail>>(result)
        Assertions.assertEquals(movieDetails, result.getOrNull())
        verify(repository).fetchMovieDetails(movieId)
    }

    @Test
    fun `invoke should return Result-Failure when repository throws an exception`() = runTest {
        // Arrange
        val movieId = 1
        val exception = RuntimeException("API error")
        `when`(repository.fetchMovieDetails(movieId)).thenReturn(Result.failure(exception))

        // Act
        val result = fetchMovieDetailsUseCase(movieId)

        // Assert
        assertTrue(result.isFailure)
        Assertions.assertEquals("API error", result.exceptionOrNull()?.message)
        verify(repository).fetchMovieDetails(movieId)
    }

    @Test
    fun `invoke should return Result-Failure for invalid movie ID`() = runTest {
        // Arrange
        val movieId = -1
        val exception = IllegalArgumentException("Invalid movie ID")
        `when`(repository.fetchMovieDetails(movieId)).thenReturn(Result.failure(exception))

        // Act
        val result = fetchMovieDetailsUseCase(movieId)

        // Assert
        assertTrue(result.isFailure)
        Assertions.assertEquals("Invalid movie ID", result.exceptionOrNull()?.message)
        verify(repository).fetchMovieDetails(movieId)
    }

    @Test
    fun `invoke should handle null values gracefully`() = runTest {
        // Arrange
        val movieId = 1
        val movieDetails = MovieDetail(
            id = movieId,
            title = "Movie 1",
            releaseDate = null,
            overview = null,
            genres = null,
            runtime = null,
            posterPath = null,
            voteAverage = null,
            voteCount = null,
            language = null,
            rating = null
        )
        `when`(repository.fetchMovieDetails(movieId)).thenReturn(Result.success(movieDetails))

        // Act
        val result = fetchMovieDetailsUseCase(movieId)

        // Assert
        assertTrue(result.isSuccess)
        val resultDetails = result.getOrNull()
        assertNotNull(resultDetails)
        Assertions.assertNull(resultDetails?.releaseDate)
        Assertions.assertNull(resultDetails?.genres)
        verify(repository).fetchMovieDetails(movieId)
    }
}