package mx.ipn.escom.bautistas.peliculas.model

import kotlinx.serialization.Serializable

@Serializable
data class Date(
    val maximum: String,
    val minimum: String
)

