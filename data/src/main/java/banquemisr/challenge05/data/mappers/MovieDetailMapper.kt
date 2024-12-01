package banquemisr.challenge05.data.mappers

import banquemisr.challenge05.data.BuildConfig
import banquemisr.challenge05.data.models.MovieDetailResponse
import banquemisr.challenge05.domain.entities.MovieDetail
import banquemisr.challenge05.domain.repositories.Mapper

class MovieDetailMapper : Mapper<MovieDetailResponse, MovieDetail> {
    override fun toDomain(dto: MovieDetailResponse) =
        dto.run {
            MovieDetail(
                id = id ?: 0,
                title = title ?: "",
                releaseDate = releaseDate,
                posterPath = BuildConfig.IMAGE_BASE_URL + posterPath,
                overview = overview,
                genres = genres?.map { it?.name ?: "" },
                runtime = runtime,
                voteAverage = voteAverage,
                voteCount = voteCount,
                rating = voteAverage?.div(2),
                language = spokenLanguages?.map { it?.name }?.joinToString(", ") { it ?: "" }
            )
        }
}