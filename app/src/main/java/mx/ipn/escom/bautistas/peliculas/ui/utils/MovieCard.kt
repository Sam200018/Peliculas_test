package mx.ipn.escom.bautistas.peliculas.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import mx.ipn.escom.bautistas.peliculas.R
import mx.ipn.escom.bautistas.peliculas.model.Pelicula

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    pelicula: Pelicula,
    goToDetails:(Int)->Unit
) {
    Column(
        modifier = modifier
            .width(200.dp)
            .height(330.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard() {
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .height(250.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500/${pelicula.backdropPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Text(text = "${pelicula.voteAverage}")
        }
        TextButton(onClick = {
            goToDetails(pelicula.id)
        }) {
            Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(
                    text = pelicula.title,
                    modifier = modifier.align(Alignment.Start)
                )

            }
        }
    }
}
