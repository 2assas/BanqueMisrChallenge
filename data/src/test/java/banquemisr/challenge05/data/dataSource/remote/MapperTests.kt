package banquemisr.challenge05.data.dataSource.remote

import banquemisr.challenge05.data.dataSource.local.entities.MovieDetailEntity
import banquemisr.challenge05.data.dataSource.local.entities.MovieEntity
import banquemisr.challenge05.data.dataSource.local.mappers.MovieDetailLocalMapper
import banquemisr.challenge05.data.dataSource.local.mappers.MovieListLocalMapper
import banquemisr.challenge05.data.mappers.MovieDetailMapper
import banquemisr.challenge05.data.mappers.MovieListMapper
import banquemisr.challenge05.data.models.MovieDetailResponse
import banquemisr.challenge05.data.models.MovieResponse
import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.entities.MovieDetail
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MapperTests {

    // Remote Mapper Tests
    @Test
    fun `should correctly map MovieDto to Movie`() {
        val movieDto = MovieResponse(
            movieDtoList = listOf(
                MovieResponse.MovieDto(
                    id = 1,
                    title = "Inception",
                    releaseDate = "2010-07-16",
                    posterPath = "/image.jpg",
                    voteAverage = 8.8
                )
            )
        )

        val expectedMovie = listOf(
            Movie(
                id = 1,
                title = "Inception",
                releaseDate = "2010-07-16",
                posterPath = "/image.jpg",
                voteAverage = 8.8
            )
        )

        val mapper = MovieListMapper()
        val actualMovie = mapper.toDomain(movieDto)

        assertEquals(expectedMovie, actualMovie)
    }

    @Test
    fun `should correctly map MovieDetailDto to MovieDetail`() {
        val movieDetailDto = MovieDetailResponse(
            id = 1,
            title = "Inception",
            overview = "A mind-bending thriller",
            runtime = 148,
            releaseDate = "2010-07-16"
        )

        val expectedMovieDetail = MovieDetail(
            id = 1,
            title = "Inception",
            overview = "A mind-bending thriller",
            runtime = 148,
            releaseDate = "2010-07-16"
        )

        val mapper = MovieDetailMapper()
        val actualMovieDetail = mapper.toDomain(movieDetailDto)

        assertEquals(expectedMovieDetail, actualMovieDetail)
    }

    // Local Mapper Tests
    @Test
    fun `should correctly map MovieEntity to Movie`() {
        val movieEntity = MovieEntity(
            id = 1,
            title = "Inception",
            releaseDate = "2010-07-16",
            posterPath = "/image.jpg",
            voteAverage = 8.8,
            page = 1
        )

        val expectedMovie = Movie(
            id = 1,
            title = "Inception",
            releaseDate = "2010-07-16",
            posterPath = "/image.jpg",
            voteAverage = 8.8
        )

        val mapper = MovieListLocalMapper()
        val actualMovie = mapper.toDomain(movieEntity)

        assertEquals(expectedMovie, actualMovie)
    }

    @Test
    fun `should correctly map MovieDetailEntity to MovieDetail`() {
        val movieDetailEntity = MovieDetailEntity(
            id = 1,
            title = "Inception",
            overview = "A mind-bending thriller",
            runtime = 148,
            releaseDate = "2010-07-16"
        )

        val expectedMovieDetail = MovieDetail(
            id = 1,
            title = "Inception",
            overview = "A mind-bending thriller",
            runtime = 148,
            releaseDate = "2010-07-16"
        )

        val mapper = MovieDetailLocalMapper()
        val actualMovieDetail = mapper.toDomain(movieDetailEntity)

        assertEquals(expectedMovieDetail, actualMovieDetail)
    }

    @Test
    fun `should correctly map Movie to MovieEntity`() {
        val movie = Movie(
            id = 1,
            title = "Inception",
            releaseDate = "2010-07-16",
            posterPath = "/image.jpg",
            voteAverage = 8.8
        )

        val expectedMovieEntity = MovieEntity(
            id = 1,
            title = "Inception",
            releaseDate = "2010-07-16",
            posterPath = "/image.jpg",
            voteAverage = 8.8,
            page = 1
        )

        val mapper = MovieListLocalMapper()
        val actualMovieEntity = mapper.toEntity(movie).copy(page = 1)

        assertEquals(expectedMovieEntity, actualMovieEntity)
    }

    @Test
    fun `should correctly map MovieDetail to MovieDetailEntity`() {
        val movieDetail = MovieDetail(
            id = 1,
            title = "Inception",
            overview = "A mind-bending thriller",
            runtime = 148,
            releaseDate = "2010-07-16"
        )

        val expectedMovieDetailEntity = MovieDetailEntity(
            id = 1,
            title = "Inception",
            overview = "A mind-bending thriller",
            runtime = 148,
            releaseDate = "2010-07-16"
        )

        val mapper = MovieDetailLocalMapper()
        val actualMovieDetailEntity = mapper.toEntity(movieDetail)

        assertEquals(expectedMovieDetailEntity, actualMovieDetailEntity)
    }
}
