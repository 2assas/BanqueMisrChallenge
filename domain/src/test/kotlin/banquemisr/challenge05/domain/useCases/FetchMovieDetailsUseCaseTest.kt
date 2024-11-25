package banquemisr.challenge05.domain.useCases

import banquemisr.challenge05.domain.entities.DetailedMovie
import banquemisr.challenge05.domain.repositories.MovieRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import kotlin.test.assertTrue

class FetchMovieDetailsUseCaseTest {

    private val repository: MovieRepository = mock(MovieRepository::class.java)
    private val fetchMovieDetailsUseCase = FetchMovieDetailsUseCase(repository)

    @Test
    fun `invoke should return movie details when repository fetches successfully`() = runTest {
        // Arrange
        val movieId = 1
        val movieDetails = DetailedMovie(
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
        `when`(repository.fetchMovieDetails(movieId)).thenReturn(movieDetails)

        // Act
        val result = fetchMovieDetailsUseCase(movieId)

        // Assert
        Assertions.assertEquals(movieDetails, result)
        verify(repository).fetchMovieDetails(movieId)
    }

    @Test
    fun `invoke should throw exception when repository fails`() = runTest {
        // Arrange
        val movieId = 1
        val exception = RuntimeException("API error")
        `when`(repository.fetchMovieDetails(movieId)).thenThrow(exception)

        // Act & Assert
        val thrown = assertThrows<RuntimeException> {
            runBlocking { fetchMovieDetailsUseCase(movieId) }
        }
        Assertions.assertEquals("API error", thrown.message)
        verify(repository).fetchMovieDetails(movieId)
    }

    @Test
    fun `invoke should throw exception for invalid movie ID`() = runTest {
        // Arrange
        val movieId = -1
        val exception = IllegalArgumentException("Invalid movie ID")
        `when`(repository.fetchMovieDetails(movieId)).thenThrow(exception)

        // Act & Assert
        val thrown = assertThrows<IllegalArgumentException> {
            runBlocking { fetchMovieDetailsUseCase(movieId) }
        }
        assertEquals("Invalid movie ID", thrown.message)
        verify(repository).fetchMovieDetails(movieId)
    }

    @Test
    fun `invoke should handle null values gracefully`() = runBlocking {
        // Arrange
        val movieId = 1
        val movieDetails = DetailedMovie(
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
        `when`(repository.fetchMovieDetails(movieId)).thenReturn(movieDetails)

        // Act
        val result = fetchMovieDetailsUseCase(movieId)

        // Assert
        Assertions.assertNotNull(result)
        Assertions.assertNull(result.releaseDate)
        Assertions.assertNull(result.genres)
    }

    @Test
    fun `invoke should handle missing fields in movie details`() = runTest {
        // Arrange
        val movieDetails = DetailedMovie(
            id = 1,
            title = "Movie 1", // Missing title
            genres = listOf("Action"),
            runtime = null, // Missing runtime
            posterPath = null, // Missing posterPath
            voteAverage = null, // Missing voteAverage
            voteCount = 0,
            language = null,
            rating = 3.4
        )
        `when`(repository.fetchMovieDetails(1)).thenReturn(movieDetails)

        // Act
        val result = fetchMovieDetailsUseCase(1)

        // Assert
        Assertions.assertNotNull(result)
        Assertions.assertEquals(0, result.voteCount)
        result.genres?.isNotEmpty()?.let { assertTrue(it) }
    }

}