package mx.ipn.escom.bautistas.peliculas.network

import mx.ipn.escom.bautistas.peliculas.model.MovieResponse
import mx.ipn.escom.bautistas.peliculas.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PeliculasApiService {

    @GET("movie/top_rated")
    suspend fun getTopMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Response

    @GET("movie/now_playing")
    suspend fun getOnCinema(@Query("api_key") apiKey: String, @Query("page") page: Int): Response

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String
    ): MovieResponse
}