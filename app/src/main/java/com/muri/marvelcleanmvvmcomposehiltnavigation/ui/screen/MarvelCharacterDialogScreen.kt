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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.muri.domain.entity.MarvelCharacter
import com.muri.marvelcleanmvvmcomposehiltnavigation.R
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.CardContentText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.CardDescriptionText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.RemoteImage
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
                        )
                ) {
                    val (id, image, name, description) = createRefs()
                    createVerticalChain(image, name, description)

                    CardContentText(
                        text = stringResource(id = R.string.marvel_character_detail_screen_id, marvelCharacter.id),
                        modifier = Modifier
                            .padding(10.dp)
                            .constrainAs(id) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                            }
                    )
                    RemoteImage(
                        imgPath = marvelCharacter.img,
                        contentDescription = stringResource(id = R.string.marvel_character_detail_screen_content_description),
                        modifier = Modifier
                            .constrainAs(image) {
                                start.linkTo(parent.start)
                                top.linkTo(id.bottom)
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
                                bottom.linkTo(description.top)
                            }
                    )
                    CardDescriptionText(
                        text = marvelCharacter.description.ifEmpty {
                            stringResource(id = R.string.marvel_character_detail_screen_no_description)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .constrainAs(description) {
                                start.linkTo(parent.start)
                                top.linkTo(name.bottom)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarvelCharacterDialogScreenPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        MarvelCharacterDialogScreen(MarvelCharacter(9292, "Muriman", "Es genial", "")) { }
    }
}
