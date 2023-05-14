package mx.ipn.escom.bautistas.peliculas.ui

import androidx.annotation.StringRes
import mx.ipn.escom.bautistas.peliculas.R

sealed class Routes(val route: String, @StringRes val title: Int) {
  object SplashScreen : Routes("SplashScreen", R.string.splash_screen)
  object Home : Routes("Home",R.string.app_name)
  object Details : Routes("Details",R.string.details)
}
