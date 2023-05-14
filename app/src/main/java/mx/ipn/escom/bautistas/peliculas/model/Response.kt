package mx.ipn.escom.bautistas.peliculas.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val dates: Date= Date("",""),
    val page: Int,
    val results: List<Pelicula>,
    @SerialName("total_pages")
    val totalPages:Int,
    @SerialName("total_results")
    val totalResults: Int
)
