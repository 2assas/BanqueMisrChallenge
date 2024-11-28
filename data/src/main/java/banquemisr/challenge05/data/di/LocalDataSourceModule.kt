package banquemisr.challenge05.data.di

import banquemisr.challenge05.data.dataSource.local.MovieDatabase
import banquemisr.challenge05.data.dataSource.local.MovieLocalDataSource
import banquemisr.challenge05.data.dataSource.local.mappers.MovieDetailLocalMapper
import banquemisr.challenge05.data.dataSource.local.mappers.MovieListLocalMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideMovieListMapper(): MovieListLocalMapper {
        return MovieListLocalMapper()
    }

    @Provides
    @Singleton
    fun provideMovieDetailMapper(): MovieDetailLocalMapper {
        return MovieDetailLocalMapper()
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        movieDatabase: MovieDatabase,
        movieListLocalMapper: MovieListLocalMapper,
        movieDetailLocalMapper: MovieDetailLocalMapper
    ): MovieLocalDataSource {
        return MovieLocalDataSource(movieDatabase, movieListLocalMapper, movieDetailLocalMapper)
    }
}
