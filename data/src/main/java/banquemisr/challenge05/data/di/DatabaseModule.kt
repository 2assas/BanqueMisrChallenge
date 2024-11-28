package banquemisr.challenge05.data.di

import android.content.Context
import banquemisr.challenge05.data.dataSource.local.MovieDatabase
import banquemisr.challenge05.data.dataSource.local.dao.MovieDetailDao
import banquemisr.challenge05.data.dataSource.local.dao.MovieListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideMovieListDao(movieDatabase: MovieDatabase): MovieListDao {
        return movieDatabase.movieListDao()
    }

    @Provides
    @Singleton
    fun provideMovieDetailDao(movieDatabase: MovieDatabase): MovieDetailDao {
        return movieDatabase.movieDetailDao()
    }
}
