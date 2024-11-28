package banquemisr.challenge05.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import banquemisr.challenge05.data.dataSource.local.entities.MovieDetailEntity

@Dao
interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movie: MovieDetailEntity)

    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailEntity?
}