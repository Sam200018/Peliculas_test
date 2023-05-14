package mx.ipn.escom.bautistas.peliculas.ui.utils

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mx.ipn.escom.bautistas.peliculas.ui.Routes

@Composable
fun PeliculasTopBar(
    modifier: Modifier= Modifier,
    currentRoute: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = currentRoute)} ,
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack && currentRoute.contains(Routes.Details.route)) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }
        }
    )
}