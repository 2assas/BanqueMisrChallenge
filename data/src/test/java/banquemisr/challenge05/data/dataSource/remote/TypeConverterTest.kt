package banquemisr.challenge05.data.dataSource.remote

import banquemisr.challenge05.data.dataSource.local.Converters
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TypeConverterTest {

    private val converter = Converters()

    @Test
    fun `should convert genres list to string and back`() {
        // Arrange
        val genres = listOf("Action", "Drama")

        // Act
        val genresString = converter.fromGenres(genres)
        val result = converter.toGenres(genresString)

        // Assert
        assertEquals(genres, result)
    }
}
