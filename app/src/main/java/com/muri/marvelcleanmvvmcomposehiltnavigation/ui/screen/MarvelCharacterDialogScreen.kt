package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import com.muri.domain.entity.MarvelCharacter
import com.muri.marvelcleanmvvmcomposehiltnavigation.R
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.CardContentText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.CardDescriptionText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.RemoteImage
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDialogScreenId.CHARACTER_LAYOUT_ID
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDialogScreenId.DESCRIPTION_LAYOUT_ID
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDialogScreenId.IMAGE_LAYOUT_ID
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDialogScreenId.NAME_LAYOUT_ID
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.GradientEnd
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.GradientStart
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterDialogViewModel
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterDialogViewModel.MarvelCharacterDialogData
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterDialogViewModel.MarvelCharacterDialogState

@Composable
fun MarvelCharacterDialogScreen(
    marvelCharacter: MarvelCharacter,
    viewModel: MarvelCharacterDialogViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val data: MarvelCharacterDialogData = viewModel.state.collectAsState().value

    when (data.state) {
        MarvelCharacterDialogState.DRAW -> DrawScreen(viewModel, marvelCharacter)
        MarvelCharacterDialogState.ON_DIALOG_DISMISSED -> {
            LaunchedEffect(Unit) {
                onDismiss()
            }
        }
    }
}

@Composable
fun DrawScreen(viewModel: MarvelCharacterDialogViewModel, marvelCharacter: MarvelCharacter) {
    var showDialog by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    val verticalGradient = Brush.verticalGradient(colors = listOf(GradientStart, GradientEnd))

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                viewModel.onDismiss()
                showDialog = false
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 50.dp),
                shape = RoundedCornerShape(topEnd = 75.dp, bottomStart = 75.dp),
                border = BorderStroke(8.dp, Color.Black)
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = verticalGradient)
                        .scrollable(
                            state = scrollState,
                            orientation = Orientation.Vertical
                        ),
                    constraintSet = getScreenConstraintSet()
                ) {
                    CardContentText(
                        text = stringResource(id = R.string.marvel_character_detail_screen_id, marvelCharacter.id),
                        modifier = Modifier
                            .layoutId(CHARACTER_LAYOUT_ID)
                            .padding(10.dp)
                    )
                    RemoteImage(
                        imgPath = marvelCharacter.img,
                        contentDescription = stringResource(id = R.string.marvel_character_detail_screen_content_description),
                        modifier = Modifier.layoutId(IMAGE_LAYOUT_ID)
                    )
                    CardContentText(
                        text = marvelCharacter.name,
                        modifier = Modifier
                            .layoutId(NAME_LAYOUT_ID)
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    )
                    CardDescriptionText(
                        text = marvelCharacter.description.ifEmpty {
                            stringResource(id = R.string.marvel_character_detail_screen_no_description)
                        },
                        modifier = Modifier
                            .layoutId(DESCRIPTION_LAYOUT_ID)
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    )
                }
            }
        }
    }
}

private fun getScreenConstraintSet() = ConstraintSet {
    val id = createRefFor(CHARACTER_LAYOUT_ID)
    val image = createRefFor(IMAGE_LAYOUT_ID)
    val name = createRefFor(NAME_LAYOUT_ID)
    val description = createRefFor(DESCRIPTION_LAYOUT_ID)

    createVerticalChain(image, name, description)

    constrain(id) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
    }
    constrain(image) {
        start.linkTo(parent.start)
        top.linkTo(id.bottom)
        end.linkTo(parent.end)
        bottom.linkTo(name.top)
    }
    constrain(name) {
        start.linkTo(parent.start)
        top.linkTo(image.bottom)
        end.linkTo(parent.end)
        bottom.linkTo(description.top)
    }
    constrain(description) {
        start.linkTo(parent.start)
        top.linkTo(name.bottom)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
    }
}

@Preview(showBackground = true)
@Composable
private fun MarvelCharacterDialogScreenPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        MarvelCharacterDialogScreen(MarvelCharacter(9292, "Muriman", "Es genial", "")) { }
    }
}

private object MarvelCharacterDialogScreenId {
    const val CHARACTER_LAYOUT_ID = "CHARACTER_LAYOUT_ID"
    const val IMAGE_LAYOUT_ID = "IMAGE_LAYOUT_ID"
    const val NAME_LAYOUT_ID = "NAME_LAYOUT_ID"
    const val DESCRIPTION_LAYOUT_ID = "DESCRIPTION_LAYOUT_ID"
}
