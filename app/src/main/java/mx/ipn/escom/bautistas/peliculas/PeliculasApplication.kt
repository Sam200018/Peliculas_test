package mx.ipn.escom.bautistas.peliculas

import android.app.Application
import androidx.room.Room
import mx.ipn.escom.bautistas.peliculas.data.AppContainer
import mx.ipn.escom.bautistas.peliculas.data.DefaultAppContainer
import mx.ipn.escom.bautistas.peliculas.local.PeliculasLocalService

class PeliculasApplication: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container= DefaultAppContainer()
    }

    val database: PeliculasLocalService by lazy {
        Room.databaseBuilder(this, PeliculasLocalService::class.java,"peliculas_db").build()
    }
}