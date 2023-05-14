package mx.ipn.escom.bautistas.peliculas

import android.app.Application
import mx.ipn.escom.bautistas.peliculas.data.AppContainer
import mx.ipn.escom.bautistas.peliculas.data.DefaultAppContainer

class PeliculasApplication: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container= DefaultAppContainer()
    }
}