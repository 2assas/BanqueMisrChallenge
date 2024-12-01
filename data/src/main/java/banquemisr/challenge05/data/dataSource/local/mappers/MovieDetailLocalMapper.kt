package banquemisr.challenge05.data.dataSource.local.mappers

import banquemisr.challenge05.data.dataSource.local.entities.MovieDetailEntity
import banquemisr.challenge05.domain.entities.MovieDetail
import banquemisr.challenge05.domain.repositories.Mapper

class MovieDetailLocalMapper : Mapper<MovieDetailEntity, MovieDetail> {

    // Convert MovieDetailEntity to MovieDetail (from local entity to domain model)
    override fun toDomain(dto: MovieDetailEntity): MovieDetail {
        return MovieDetail(
            id = dto.id,
            title = dto.title?: "",
            releaseDate = dto.releaseDate,
            posterPath = dto.posterPath,
            overview = dto.overview,
            genres = dto.genres ?: "",
            runtime = dto.runtime,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            rating = dto.rating,
            language = dto.language
        )
    }

    // Convert MovieDetail to MovieDetailEntity (from domain model to local entity)
    fun toEntity(movieDetail: MovieDetail): MovieDetailEntity {
        return MovieDetailEntity(
            id = movieDetail.id,
            title = movieDetail.title,
            releaseDate = movieDetail.releaseDate,
            posterPath = movieDetail.posterPath,
            overview = movieDetail.overview,
            genres = movieDetail.genres,
            runtime = movieDetail.runtime,
            voteAverage = movieDetail.voteAverage,
            voteCount = movieDetail.voteCount,
            rating = movieDetail.rating,
            language = movieDetail.language
        )
    }
}
