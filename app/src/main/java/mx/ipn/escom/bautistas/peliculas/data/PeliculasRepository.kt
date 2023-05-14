package mx.ipn.escom.bautistas.peliculas.data

import mx.ipn.escom.bautistas.peliculas.model.MovieResponse
import mx.ipn.escom.bautistas.peliculas.model.Response
import mx.ipn.escom.bautistas.peliculas.network.PeliculasApiService

interface PeliculasRepository {
    suspend fun getTopMoviesData(apikey: String, page: Int): Response
    suspend fun getOnCinemaData(apikey: String, page: Int): Response
    suspend fun getMovieDetail(movieID: Int, apikey: String): MovieResponse

}

class NetworkPeliculasRepository(
    private val peliculaApiService: PeliculasApiService
) : PeliculasRepository {
    override suspend fun getTopMoviesData(apikey: String, page: Int): Response =
        peliculaApiService.getTopMovies(apiKey = apikey, page = page)

    override suspend fun getOnCinemaData(apikey: String, page: Int): Response =
        peliculaApiService.getOnCinema(apiKey = apikey, page = page)

    override suspend fun getMovieDetail(movieID: Int, apikey: String): MovieResponse =
        peliculaApiService.getMovieDetail(movieID = movieID, apiKey = apikey)

}