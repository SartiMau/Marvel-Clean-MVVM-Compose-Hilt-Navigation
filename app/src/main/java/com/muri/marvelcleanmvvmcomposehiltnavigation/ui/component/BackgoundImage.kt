package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.muri.marvelcleanmvvmcomposehiltnavigation.R
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.background_screen),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}

@Preview
@Composable
private fun ContentTextPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        BackgroundImage()
    }
}
