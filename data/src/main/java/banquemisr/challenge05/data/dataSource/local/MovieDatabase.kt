package banquemisr.challenge05.data.dataSource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import banquemisr.challenge05.data.dataSource.local.dao.MovieDetailDao
import banquemisr.challenge05.data.dataSource.local.dao.MovieListDao
import banquemisr.challenge05.data.dataSource.local.entities.MovieDetailEntity
import banquemisr.challenge05.data.dataSource.local.entities.MovieEntity

@Database(entities = [MovieEntity::class, MovieDetailEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieListDao(): MovieListDao
    abstract fun movieDetailDao(): MovieDetailDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
