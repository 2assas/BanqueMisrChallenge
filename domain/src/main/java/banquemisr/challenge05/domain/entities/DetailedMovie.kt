package banquemisr.challenge05.domain.entities


data class DetailedMovie (
    val id: Long,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val overview: String,
    val genres: List<String>,
    val runtime: Int,
    val voteAverage: Double,
    val voteCount: Int,
    val rating: Double,
    val language: String
)

