package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme

@Composable
fun TitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        color = Color.Red,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun TitleTextPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        TitleText(
            text = "Title",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ContentText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        color = Color.Red,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun ContentTextPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        ContentText(
            text = "Content",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CardContentText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun CardContentTextPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        CardContentText(
            text = "Content",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CardDescriptionText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        color = Color.White,
        fontSize = 15.sp,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun CardDescriptionTextPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        CardDescriptionText(
            text = "Content",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
