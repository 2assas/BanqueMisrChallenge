package banquemisr.challenge05.data.dataSource.remote

import banquemisr.challenge05.data.models.MovieDetailResponse
import banquemisr.challenge05.data.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("page") page: Int
    ): MovieResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailResponse
}
