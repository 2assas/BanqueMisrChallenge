package banquemisr.challenge05.data.dataSource.local.mappers

import banquemisr.challenge05.data.dataSource.local.entities.MovieEntity
import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.repositories.Mapper

class MovieListLocalMapper : Mapper<MovieEntity, Movie> {

    // Convert MovieEntity to Movie (from local entity to domain model)
    override fun toDomain(dto: MovieEntity): Movie {
        return Movie(
            id = dto.id,
            title = dto.title,
            releaseDate = dto.releaseDate,
            posterPath = dto.posterPath,
            voteAverage = dto.voteAverage
        )
    }

    // Convert Movie to MovieEntity (from domain model to local entity)
    fun toEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            id = movie.id,
            title = movie.title,
            releaseDate = movie.releaseDate,
            posterPath = movie.posterPath,
            voteAverage = movie.voteAverage,
            page = 1 // Assuming page information is available from pagination
        )
    }
}
