package mx.ipn.escom.bautistas.peliculas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.filter
import mx.ipn.escom.bautistas.peliculas.R
import mx.ipn.escom.bautistas.peliculas.domain.PeliculasUiState
import mx.ipn.escom.bautistas.peliculas.domain.PeliculasViewModel
import mx.ipn.escom.bautistas.peliculas.model.Pelicula
import mx.ipn.escom.bautistas.peliculas.ui.Routes
import mx.ipn.escom.bautistas.peliculas.ui.utils.MovieCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    peliculasUiState: PeliculasUiState,
    peliculasViewModel: PeliculasViewModel,
    navController: NavController
) {

    when (peliculasUiState) {
        is PeliculasUiState.Success -> MovieLists(
            topMovies = peliculasUiState.topMovies,
            onCinema = peliculasUiState.onCinema,
            peliculasViewModel = peliculasViewModel,
            navController = navController
        )

        is PeliculasUiState.Error -> ErrorScreen(retryAction = {})
        is PeliculasUiState.Loading -> LoadingScreen()
    }
}

@Composable
fun MovieLists(
    modifier: Modifier = Modifier,
    topMovies: List<Pelicula>,
    onCinema: List<Pelicula>,
    peliculasViewModel: PeliculasViewModel,
    navController: NavController
) {
    val topMoviesListState = rememberLazyListState()
    val onCinemaListState = rememberLazyListState()

    Column(
        modifier.fillMaxSize(),
    ) {
        Text(text = stringResource(id = R.string.top_movies_label))
        LazyRow(
            state = topMoviesListState,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(topMovies.size) {
                MovieCard(pelicula = topMovies[it]) { movieID ->
                    navController.navigate(Routes.Details.route + "/$movieID")
                }
            }
        }
        LaunchedEffect(key1 = topMoviesListState) {
            snapshotFlow { topMoviesListState.firstVisibleItemIndex }.filter { visibleIndex ->
                val loadThreshold = 3
                visibleIndex + loadThreshold >= topMovies.size
            }.collect {
                peliculasViewModel.loadMoreTopMovies()
            }
        }
        Text(text = stringResource(id = R.string.on_cinema_label))
        LazyRow(
            state = onCinemaListState,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(onCinema.size) {
                MovieCard(pelicula = onCinema[it]) { movieID ->
                    navController.navigate(Routes.Details.route + "/$movieID")
                }
            }
        }
        LaunchedEffect(key1 = onCinemaListState) {
            snapshotFlow { onCinemaListState.firstVisibleItemIndex }.filter { visibleIndex ->
                val loadThreshold = 3
                visibleIndex + loadThreshold >= onCinema.size
            }.collect {
                peliculasViewModel.loadMoreOnCinema()
            }
        }

    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = "Loading"
        )
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    retryAction: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.failed_to_load))
        Button(onClick = retryAction) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}
