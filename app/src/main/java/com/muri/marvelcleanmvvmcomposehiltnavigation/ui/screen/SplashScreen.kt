package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.muri.marvelcleanmvvmcomposehiltnavigation.R
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme

@Composable
fun SplashScreen(onAnimationFinish: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_screen))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        restartOnPlay = false
    )

    LottieAnimation(
        modifier = Modifier.fillMaxSize(),
        composition = composition,
        progress = { progress },
        contentScale = ContentScale.FillBounds
    )

    LaunchedEffect(progress) {
        if (progress >= 1f) {
            onAnimationFinish()
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        SplashScreen { }
    }
}
