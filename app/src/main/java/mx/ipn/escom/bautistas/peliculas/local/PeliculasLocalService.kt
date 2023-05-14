package mx.ipn.escom.bautistas.peliculas.local


import androidx.room.Database
import androidx.room.RoomDatabase
import mx.ipn.escom.bautistas.peliculas.data.PeliculasDao
import mx.ipn.escom.bautistas.peliculas.data.PeliculasLocal


@Database(entities = [PeliculasLocal::class], version = 1)
abstract class PeliculasLocalService: RoomDatabase(){
    abstract fun peliculasDao():PeliculasDao
}