package mx.ipn.escom.bautistas.peliculas.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import mx.ipn.escom.bautistas.peliculas.R

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
) {
    Column(horizontalAlignment =Alignment.CenterHorizontally) {
        ElevatedCard() {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Hola",
            )
            Text(text = "Puntuacion")
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Hola")
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    MovieCard()
}