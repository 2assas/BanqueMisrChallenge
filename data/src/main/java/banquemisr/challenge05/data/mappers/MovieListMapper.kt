package banquemisr.challenge05.data.mappers

import banquemisr.challenge05.data.BuildConfig
import banquemisr.challenge05.data.models.MovieResponse
import banquemisr.challenge05.domain.entities.Movie
import banquemisr.challenge05.domain.repositories.Mapper

class MovieListMapper : Mapper<MovieResponse, List<Movie>> {
    override fun toDomain(dto: MovieResponse) =
        dto.movieDtoList?.map {
            Movie(
                id = it?.id ?: 0,
                title = it?.title ?: "",
                releaseDate = it?.releaseDate,
                posterPath = BuildConfig.IMAGE_BASE_URL + it?.posterPath,
                voteAverage = it?.voteAverage
            )
        } ?: emptyList()
}