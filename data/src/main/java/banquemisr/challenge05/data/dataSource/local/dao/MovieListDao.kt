package banquemisr.challenge05.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import banquemisr.challenge05.data.dataSource.local.entities.MovieEntity

@Dao
interface MovieListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie_list WHERE page = :pageNumber")
    suspend fun getMoviesByPage(pageNumber: Int): List<MovieEntity>
}
