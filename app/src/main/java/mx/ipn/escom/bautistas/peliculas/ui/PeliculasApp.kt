package mx.ipn.escom.bautistas.peliculas.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.ipn.escom.bautistas.peliculas.domain.MovieDetailsViewModel
import mx.ipn.escom.bautistas.peliculas.domain.PeliculasViewModel
import mx.ipn.escom.bautistas.peliculas.ui.screens.DetailsScreen
import mx.ipn.escom.bautistas.peliculas.ui.screens.HomeScreen
import mx.ipn.escom.bautistas.peliculas.ui.screens.SplashScreen
import mx.ipn.escom.bautistas.peliculas.ui.utils.PeliculasTopBar


@Composable
fun PeliculasApp(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ""

    Scaffold(modifier = modifier, topBar = {
        PeliculasTopBar(
            currentRoute = currentRoute,
            canNavigateBack = navController.previousBackStackEntry != null
        ) {
            navController.navigateUp()
        }
    }) {
        Box(modifier = modifier.padding(it)) {
            NavHost(
                navController = navController,
                modifier = Modifier.padding(10.dp),
                startDestination = Routes.SplashScreen.route
            ) {
                composable(Routes.SplashScreen.route) {
                    SplashScreen(navController = navController)
                }
                composable(Routes.Home.route) {
                    val peliculasViewModel: PeliculasViewModel =
                        viewModel(factory = PeliculasViewModel.Factory)

                    HomeScreen(
                        peliculasUiState = peliculasViewModel.peliculasUiState,
                        peliculasViewModel = peliculasViewModel,
                        navController = navController
                    )
                }
                composable(
                    Routes.Details.route + "/{movie_id}"
                ) { backStackEntry ->
                    val idPelicula = backStackEntry.arguments?.getString("movie_id") ?: "0"
                    val movieDetailsViewModel: MovieDetailsViewModel =
                        viewModel(factory = MovieDetailsViewModel.createFactory(idPelicula = idPelicula))

                    DetailsScreen(movieDetailUiState = movieDetailsViewModel.movieDetailUiState)
                }
            }

        }

    }
}