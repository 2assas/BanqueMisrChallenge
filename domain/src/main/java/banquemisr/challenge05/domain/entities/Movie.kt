package banquemisr.challenge05.domain.entities

data class Movie (
    val id: Int,
    val title: String? = null,
    val releaseDate: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double? = null
)