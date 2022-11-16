package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.muri.domain.entity.MarvelCharacter
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.GradientEnd
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.GradientStart
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme

@Composable
fun MarvelCard(marvelCharacter: MarvelCharacter, onCardClicked: (marvelCharacter: MarvelCharacter) -> Unit) {
    val horizontalGradient = Brush.horizontalGradient(colors = listOf(GradientStart, GradientEnd))

    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onCardClicked(marvelCharacter) }
            .padding(vertical = 15.dp),
        shape = RoundedCornerShape(topEnd = 75.dp, bottomEnd = 75.dp),
        border = BorderStroke(8.dp, Color.Black)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = horizontalGradient)
                .padding(15.dp),
        ) {
            val (image, name) = createRefs()
            createVerticalChain(image, name)

            RemoteImage(
                imgPath = marvelCharacter.img,
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(name.top)
                    }
            )
            CardContentText(
                text = marvelCharacter.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .constrainAs(name) {
                        start.linkTo(parent.start)
                        top.linkTo(image.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarvelCardPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        MarvelCard(MarvelCharacter(9292, "Muriman", "Es genial", "")) { }
    }
}
