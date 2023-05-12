package mx.ipn.escom.bautistas.peliculas

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.ipn.escom.bautistas.peliculas.ui.PeliculasRoutes
import mx.ipn.escom.bautistas.peliculas.ui.screens.HomeScreen
import mx.ipn.escom.bautistas.peliculas.ui.screens.SplashScreen


@Composable
fun PeliculasApp(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    Scaffold(modifier = modifier) {
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = PeliculasRoutes.SplashScreen.name
        ) {
            composable(PeliculasRoutes.SplashScreen.name){
                SplashScreen(navController = navController)
            }
            composable(PeliculasRoutes.Home.name){
                HomeScreen()
            }
        }

    }
}