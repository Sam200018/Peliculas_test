package mx.ipn.escom.bautistas.peliculas.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import mx.ipn.escom.bautistas.peliculas.R
import mx.ipn.escom.bautistas.peliculas.domain.MovieDetailUiState
import mx.ipn.escom.bautistas.peliculas.model.MovieResponse
import mx.ipn.escom.bautistas.peliculas.ui.theme.AccentYellow

@Composable
fun DetailsScreen(modifier: Modifier = Modifier, movieDetailUiState: MovieDetailUiState) {
    when (movieDetailUiState) {
        is MovieDetailUiState.Success -> MovieSuccess(movieResponse = movieDetailUiState.movie)
        is MovieDetailUiState.Loading -> LoadingScreen()
        is MovieDetailUiState.Error -> ErrorScreen() {

        }
    }


}

@Composable
fun MovieSuccess(modifier: Modifier = Modifier, movieResponse: MovieResponse) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .height(250.dp),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500/${movieResponse.posterPath}")
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            error = painterResource(id = R.drawable.ic_launcher_foreground),
            placeholder = painterResource(id = R.drawable.loading_img)
        )

        Text(text = movieResponse.originalTitle)
        RatingStars(rating = movieResponse.voteAverage)
        Text(
            text = movieResponse.overview ?: "",
            modifier
                .fillMaxWidth()
                .align(Alignment.Start),
        )
    }
}


@Composable
fun RatingStars(
    modifier: Modifier = Modifier,
    rating: Double,
    maxRating: Int = 5
) {
    val auxRating = (rating * maxRating) / 10

    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(maxRating) { index ->
            val color = if (index < auxRating) AccentYellow else Color.Black
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }

    }
}
