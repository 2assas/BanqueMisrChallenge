package banquemisr.challenge05.data.dataSource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_list")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String? = null,
    val releaseDate: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double? = null,
    val page: Int = 0
)