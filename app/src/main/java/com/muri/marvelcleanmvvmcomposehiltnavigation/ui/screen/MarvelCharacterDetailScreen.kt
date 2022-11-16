package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.muri.domain.entity.MarvelCharacter
import com.muri.marvelcleanmvvmcomposehiltnavigation.R
import com.muri.marvelcleanmvvmcomposehiltnavigation.mvvm.MarvelCharacterDetailViewModel
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.BackgroundImage
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.CardContentText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.ContentText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.ErrorDialog
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.Loader
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.RemoteImage
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme

@Composable
fun MarvelCharacterDetailScreen(
    viewModel: MarvelCharacterDetailViewModel = hiltViewModel(),
    marvelCharacterId: Int,
    onImageClicked: (marvelCharacter: MarvelCharacter) -> Unit,
    onError: () -> Unit
) {
    val data: MarvelCharacterDetailViewModel.Data = viewModel.state.collectAsState().value
    viewModel.getCharacters(marvelCharacterId)

    BackgroundImage()

    when (data.state) {
        MarvelCharacterDetailViewModel.State.LOADING -> {
            Loader()
        }
        MarvelCharacterDetailViewModel.State.SHOW_CHARACTER -> {
            data.marvelCharacter?.let { ShowCharacter(it, onImageClicked) }
        }
        MarvelCharacterDetailViewModel.State.ERROR -> {
            data.exception?.let { ErrorDialog(it, onError) }
        }
    }
}

@Composable
fun ShowCharacter(marvelCharacter: MarvelCharacter, onImageClicked: (marvelCharacter: MarvelCharacter) -> Unit) {
    val scrollState = rememberScrollState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
    ) {
        val (id, image, name, description, disclaimer) = createRefs()
        createVerticalChain(image, name, description, disclaimer)

        ContentText(
            text = stringResource(id = R.string.marvel_character_detail_screen_id, marvelCharacter.id),
            modifier = Modifier.constrainAs(id) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )
        RemoteImage(
            imgPath = marvelCharacter.img,
            contentDescription = stringResource(id = R.string.marvel_character_detail_screen_content_description),
            modifier = Modifier
                .clickable { onImageClicked(marvelCharacter) }
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(id.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(name.top)
                }
        )
        ContentText(
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
        ContentText(
            text = if (marvelCharacter.description.isNotEmpty()) {
                marvelCharacter.description
            } else {
                stringResource(id = R.string.marvel_character_detail_screen_no_description)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .constrainAs(description) {
                    start.linkTo(parent.start)
                    top.linkTo(name.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(disclaimer.top)
                }
        )
        CardContentText(
            text = stringResource(id = R.string.marvel_character_detail_screen_disclaimer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .constrainAs(disclaimer) {
                    start.linkTo(parent.start)
                    top.linkTo(description.bottom)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Preview
@Composable
fun MarvelCharacterDetailScreenPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        ShowCharacter(MarvelCharacter(id = 9292, name = "Muriman", description = "Qué es eso?? Es Muriman", img = "")) { }
    }
}
