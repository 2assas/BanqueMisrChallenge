package banquemisr.challenge05.data.dataSource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val title: String? = "",
    val releaseDate: String? = null,
    val posterPath: String? = null,
    val overview: String? = null,
    val genres: List<String>? = listOf(),
    val runtime: Int? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val rating: Double? = null,
    val language: String? = null
)