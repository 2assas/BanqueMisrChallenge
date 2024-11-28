package banquemisr.challenge05.data.di

import banquemisr.challenge05.data.dataSource.remote.MovieApiService
import banquemisr.challenge05.data.dataSource.remote.MovieRemoteDataSource
import banquemisr.challenge05.data.mappers.MovieDetailMapper
import banquemisr.challenge05.data.mappers.MovieListMapper
import banquemisr.challenge05.data.repositories.MovieRepositoryImpl
import banquemisr.challenge05.domain.repositories.MovieRepository
import banquemisr.challenge05.domain.useCases.FetchMovieDetailsUseCase
import banquemisr.challenge05.domain.useCases.FetchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieMapper(): MovieListMapper {
        return MovieListMapper()
    }

    @Provides
    @Singleton
    fun provideMovieDetailsMapper(): MovieDetailMapper {
        return MovieDetailMapper()
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(api: MovieApiService): MovieRemoteDataSource {
        return MovieRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource,
        movieMapper: MovieListMapper,
        movieDetailMapper: MovieDetailMapper
    ): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource, movieMapper, movieDetailMapper)
    }

    @Provides
    @Singleton
    fun provideFetchMoviesUseCase(
        repository: MovieRepository
    ): FetchMoviesUseCase {
        return FetchMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchMovieDetailsUseCase(
        repository: MovieRepository
    ): FetchMovieDetailsUseCase {
        return FetchMovieDetailsUseCase(repository)
    }

}