package banquemisr.challenge05.domain.entities


data class MovieDetail (
    val id: Int,
    val title: String,
    val releaseDate: String? = null,
    val posterPath: String? = null,
    val overview: String? = null,
    val genres: List<String>? = emptyList(),
    val runtime: Int? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val rating: Double? = null,
    val language: String? = null
)

