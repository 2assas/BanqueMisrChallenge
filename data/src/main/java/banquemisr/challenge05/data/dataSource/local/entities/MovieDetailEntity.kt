package banquemisr.challenge05.data.dataSource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val title: String?,
    val releaseDate: String?,
    val posterPath: String?,
    val overview: String?,
    val genres: List<String>?,
    val runtime: Int?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val rating: Double?,
    val language: String?
)