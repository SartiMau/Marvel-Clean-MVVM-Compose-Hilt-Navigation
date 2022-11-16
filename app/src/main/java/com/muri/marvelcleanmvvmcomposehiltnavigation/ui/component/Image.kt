package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun RemoteImage(imgPath: String, contentDescription: String, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = imgPath,
        contentDescription = contentDescription,
        modifier = modifier
            .width(200.dp)
            .height(170.dp)
            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .background(Color.Black)
            .border(BorderStroke(8.dp, Color.Black)),
        loading = {
            Loader()
        }
    )
}
