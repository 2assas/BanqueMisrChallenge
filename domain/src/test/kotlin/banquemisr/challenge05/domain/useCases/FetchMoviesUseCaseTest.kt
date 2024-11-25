package banquemisr.challenge05.domain.useCases

import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieCategory
import banquemisr.challenge05.domain.repositories.MovieRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


class FetchMoviesUseCaseTest {

    private val repository: MovieRepository = mock(MovieRepository::class.java)
    private val fetchMoviesUseCase = FetchMoviesUseCase(repository)

    @Test
    fun `invoke should return movies when repository fetches successfully`() = runTest {
        // Arrange
        val category = MovieCategory.POPULAR
        val movies = listOf(
            Movie(
                id = 1,
                title = "Movie 1",
                releaseDate = "2024-10-15",
                posterPath = "path1",
                voteAverage = 7.3
            ),
            Movie(
                id = 2,
                title = "Movie 2",
                releaseDate = "2024-11-04",
                posterPath = "path2",
                voteAverage = 8.1
            )
        )
        `when`(repository.fetchMovies(category)).thenReturn(movies)

        // Act
        val result = fetchMoviesUseCase(category)

        // Assert
        assertEquals(movies, result)
        verify(repository).fetchMovies(category)
    }

    @Test
    fun `invoke should return empty list when repository returns no movies`() = runTest {
        // Arrange
        val category = MovieCategory.UPCOMING
        `when`(repository.fetchMovies(category)).thenReturn(emptyList())

        // Act
        val result = fetchMoviesUseCase(category)

        // Assert
        assertTrue(result.isEmpty())
        verify(repository).fetchMovies(category)
    }

    @Test
    fun `invoke should throw exception when repository fails`() = runTest {
        // Arrange
        val category = MovieCategory.NOW_PLAYING
        val exception = RuntimeException("API error")
        `when`(repository.fetchMovies(category)).thenThrow(exception)

        // Act & Assert
        val thrown = assertThrows<RuntimeException> {
            runBlocking { fetchMoviesUseCase(category) }
        }
        assertEquals("API error", thrown.message)
        verify(repository).fetchMovies(category)
    }

    @Test
    fun `invoke should handle invalid data in movies`() = runTest {
        // Arrange
        val movies = listOf(
            Movie(id = -1, title = null, releaseDate = "2024-10-16", posterPath = "path1"),
            Movie(id = 2, title = "Valid Movie", releaseDate = "2024-10-16", posterPath = "path2")
        )
        `when`(repository.fetchMovies(MovieCategory.POPULAR)).thenReturn(movies)

        // Act
        val result = fetchMoviesUseCase(MovieCategory.POPULAR)

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size)
    }

}