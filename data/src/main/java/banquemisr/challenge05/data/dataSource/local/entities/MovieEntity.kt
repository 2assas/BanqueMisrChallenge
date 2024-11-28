package banquemisr.challenge05.data.dataSource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_list")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String?,
    val releaseDate: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val page: Int
)