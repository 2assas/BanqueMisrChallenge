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
                genres = genres?.map { it?.name }?.joinToString(", ") ?: "",
                runtime = formatRuntimeToHoursAndMinutes(runtime ?: 0),
                voteAverage = voteAverage,
                voteCount = voteCount,
                rating = voteAverage?.div(2),
                language = spokenLanguages?.map { it?.name }?.joinToString(", ") { it ?: "" }
            )
        }

    private fun formatRuntimeToHoursAndMinutes(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours}h ${remainingMinutes}min"
    }
}