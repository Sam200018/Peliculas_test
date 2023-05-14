package mx.ipn.escom.bautistas.peliculas.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import mx.ipn.escom.bautistas.peliculas.network.PeliculasApiService
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val peliculasRepository: PeliculasRepository
}


class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://api.themoviedb.org/3/"


    private val retrofit: Retrofit= Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService : PeliculasApiService by lazy {
        retrofit.create(PeliculasApiService::class.java)
    }

    override val peliculasRepository: PeliculasRepository by lazy {
        NetworkPeliculasRepository(retrofitService)
    }

}