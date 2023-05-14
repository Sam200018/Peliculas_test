package mx.ipn.escom.bautistas.peliculas.ui.utils

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import mx.ipn.escom.bautistas.peliculas.R
import mx.ipn.escom.bautistas.peliculas.ui.Routes

@Composable
fun PeliculasTopBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    canNavigateBack: Boolean,
    searching: String,
    filterMoviesByString: (String) -> Unit,
    navigateUp: () -> Unit,
) {
    val isSearching = rememberSaveable {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            if (isSearching.value) {
                OutlinedTextField(
                    value = searching,
                    onValueChange = {
                        filterMoviesByString(it)
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.search_field_label),
                            color = Color.White
                        )
                    }
                )
            } else {
                Text(text = currentRoute)
            }
        },
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
        },
        actions = {
            if (currentRoute == Routes.Home.route) {
                IconButton(onClick = {
                    isSearching.value = !isSearching.value
                    if (!isSearching.value) {
                        filterMoviesByString("")
                    }
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "")
                }
            }
        }
    )
}