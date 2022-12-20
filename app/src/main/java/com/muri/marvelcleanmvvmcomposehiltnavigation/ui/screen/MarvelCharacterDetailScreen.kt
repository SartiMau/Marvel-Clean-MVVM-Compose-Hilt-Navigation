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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.muri.domain.entity.MarvelCharacter
import com.muri.marvelcleanmvvmcomposehiltnavigation.R
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.BackgroundImage
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.CardContentText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.ContentText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.ErrorDialog
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.Loader
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.RemoteImage
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDetailScreenId.CHARACTER_LAYOUT_ID
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDetailScreenId.DESCRIPTION_LAYOUT_ID
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDetailScreenId.DISCLAIMER_LAYOUT_ID
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDetailScreenId.IMAGE_LAYOUT_ID
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDetailScreenId.NAME_LAYOUT_ID
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.util.OnLifecycleEvent
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterDetailViewModel

@Composable
fun MarvelCharacterDetailScreen(
    viewModel: MarvelCharacterDetailViewModel = hiltViewModel(),
    marvelCharacterId: Int,
    onImageClicked: (marvelCharacter: MarvelCharacter) -> Unit,
    onError: () -> Unit
) {
    val data: MarvelCharacterDetailViewModel.CharacterDetailData = viewModel.state.collectAsState().value

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> viewModel.getCharacter(marvelCharacterId)
            else -> {}
        }
    }

    BackgroundImage()

    when (data.state) {
        MarvelCharacterDetailViewModel.CharacterDetailState.LOADING -> {
            Loader()
        }
        MarvelCharacterDetailViewModel.CharacterDetailState.SHOW_CHARACTER -> {
            data.marvelCharacter?.let { ShowCharacter(it, onImageClicked) }
        }
        MarvelCharacterDetailViewModel.CharacterDetailState.ERROR -> {
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
            ),
        constraintSet = getScreenConstraintSet()
    ) {
        ContentText(
            text = stringResource(id = R.string.marvel_character_detail_screen_id, marvelCharacter.id),
            modifier = Modifier.layoutId(CHARACTER_LAYOUT_ID)
        )
        RemoteImage(
            imgPath = marvelCharacter.img,
            contentDescription = stringResource(id = R.string.marvel_character_detail_screen_content_description),
            modifier = Modifier
                .layoutId(IMAGE_LAYOUT_ID)
                .clickable { onImageClicked(marvelCharacter) }
        )
        ContentText(
            text = marvelCharacter.name,
            modifier = Modifier
                .layoutId(NAME_LAYOUT_ID)
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        ContentText(
            text = if (marvelCharacter.description.isNotEmpty()) {
                marvelCharacter.description
            } else {
                stringResource(id = R.string.marvel_character_detail_screen_no_description)
            },
            modifier = Modifier
                .layoutId(DESCRIPTION_LAYOUT_ID)
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        CardContentText(
            text = stringResource(id = R.string.marvel_character_detail_screen_disclaimer),
            modifier = Modifier
                .layoutId(DISCLAIMER_LAYOUT_ID)
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
    }
}

private fun getScreenConstraintSet() = ConstraintSet {
    val id = createRefFor(CHARACTER_LAYOUT_ID)
    val image = createRefFor(IMAGE_LAYOUT_ID)
    val name = createRefFor(NAME_LAYOUT_ID)
    val description = createRefFor(DESCRIPTION_LAYOUT_ID)
    val disclaimer = createRefFor(DISCLAIMER_LAYOUT_ID)

    createVerticalChain(image, name, description, disclaimer)

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
        bottom.linkTo(disclaimer.top)
    }
    constrain(disclaimer) {
        start.linkTo(parent.start)
        top.linkTo(description.bottom)
        bottom.linkTo(parent.bottom)
    }
}

@Preview
@Composable
fun MarvelCharacterDetailScreenPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        ShowCharacter(MarvelCharacter(id = 9292, name = "Muriman", description = "Qué es eso?? Es Muriman", img = "")) { }
    }
}

private object MarvelCharacterDetailScreenId {
    const val CHARACTER_LAYOUT_ID = "CHARACTER_LAYOUT_ID"
    const val IMAGE_LAYOUT_ID = "IMAGE_LAYOUT_ID"
    const val NAME_LAYOUT_ID = "NAME_LAYOUT_ID"
    const val DESCRIPTION_LAYOUT_ID = "DESCRIPTION_LAYOUT_ID"
    const val DISCLAIMER_LAYOUT_ID = "DISCLAIMER_LAYOUT_ID"
}
