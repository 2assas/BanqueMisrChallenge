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
        val page = 1
        val movies = Result.success(
            listOf(
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
        )
        `when`(repository.fetchMovies(category, page)).thenReturn(movies)

        // Act
        val result = fetchMoviesUseCase(category, page)

        // Assert
        assertEquals(movies, result)
        verify(repository).fetchMovies(category, page)
    }

    @Test
    fun `invoke should return empty list when repository returns no movies`() = runTest {
        // Arrange
        val category = MovieCategory.UPCOMING
        val page = 1
        `when`(repository.fetchMovies(category, page)).thenReturn(Result.success(emptyList()))

        // Act
        val result = fetchMoviesUseCase(category, page)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
        verify(repository).fetchMovies(category, page)
    }

    @Test
    fun `invoke should throw exception when repository fails`() = runTest {
        // Arrange
        val category = MovieCategory.NOW_PLAYING
        val page = 1
        val exception = RuntimeException("API error")
        `when`(repository.fetchMovies(category, page)).thenThrow(exception)

        // Act & Assert
        val thrown = assertThrows<RuntimeException> {
            runBlocking { fetchMoviesUseCase(category, page) }
        }
        assertEquals("API error", thrown.message)
        verify(repository).fetchMovies(category, page)
    }

    @Test
    fun `invoke should handle invalid data in movies`() = runTest {
        val page = 1
        // Arrange
        val movies = listOf(
            Movie(id = -1, title = null, releaseDate = "2024-10-16", posterPath = "path1"),
            Movie(id = 2, title = "Valid Movie", releaseDate = "2024-10-16", posterPath = "path2")
        )
        `when`(
            repository.fetchMovies(
                MovieCategory.POPULAR,
                page
            )
        ).thenReturn(Result.success(movies))

        // Act
        val result = fetchMoviesUseCase(MovieCategory.POPULAR, page)

        // Assert
        assertTrue(result.isSuccess)
        assertNotNull(result)
        assertEquals(1, result.getOrNull()?.size)
    }

}