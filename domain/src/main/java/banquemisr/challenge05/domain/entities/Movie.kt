package banquemisr.challenge05.domain.entities

data class Movie (
    val id: Long,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val voteAverage: Double
)