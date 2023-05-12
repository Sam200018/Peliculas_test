package mx.ipn.escom.bautistas.peliculas.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import mx.ipn.escom.bautistas.peliculas.R
import mx.ipn.escom.bautistas.peliculas.ui.PeliculasRoutes

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navController: NavController) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoadingAnimation(navController = navController)
        Text(text = stringResource(id = R.string.app_name))
    }
}

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp,
    navController: NavController
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    },
                )
            )
            delay(3000L)
            navController.navigate(PeliculasRoutes.Home.name)
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        circleValues.forEach { value ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = CircleShape
                    )
            )
        }
    }

}
