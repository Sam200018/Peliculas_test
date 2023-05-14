package mx.ipn.escom.bautistas.peliculas.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query


@Entity(tableName = "peliculas")
data class PeliculasLocal(
    val backdropPath: String,
    @PrimaryKey val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val type: String
)

@Dao
interface PeliculasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pelicula: PeliculasLocal)

    @Query("SELECT * from peliculas WHERE type = 'topMovies'")
    suspend fun getTopMovies(): List<PeliculasLocal>

    @Query("SELECT * from peliculas WHERE type = 'onCinema'")
    suspend fun getOnCinema(): List<PeliculasLocal>

    @Query("SELECT * from peliculas WHERE id = :id")
    suspend fun getMoveById(id: Int):PeliculasLocal

}