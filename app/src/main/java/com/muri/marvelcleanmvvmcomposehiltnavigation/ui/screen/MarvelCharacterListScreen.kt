package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.BackgroundImage
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.ErrorDialog
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.Loader
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.MarvelCard
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.TitleText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterListViewModel

@Composable
fun MarvelCharacterListScreen(
    viewModel: MarvelCharacterListViewModel = hiltViewModel(),
    onItemClicked: (marvelCharacter: MarvelCharacter) -> Unit,
    onError: () -> Unit
) {
    val data: MarvelCharacterListViewModel.CharacterListData = viewModel.state.collectAsState().value
    viewModel.getCharacters()

    BackgroundImage()

    when (data.state) {
        MarvelCharacterListViewModel.CharacterListState.LOADING -> {
            Loader()
        }
        MarvelCharacterListViewModel.CharacterListState.SHOW_CHARACTERS -> {
            ShowCharacters(data.characterList, onItemClicked)
        }
        MarvelCharacterListViewModel.CharacterListState.ERROR -> {
            data.exception?.let { ErrorDialog(it, onError) }
        }
    }
}

@Composable
private fun ShowCharacters(characterList: List<MarvelCharacter>, onItemClicked: (marvelCharacter: MarvelCharacter) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp, end = 15.dp, bottom = 15.dp)
    ) {
        val (title, content) = createRefs()
        createVerticalChain(title, content)

        TitleText(
            text = stringResource(id = R.string.marvel_character_list_screen_button),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(content.top)
                }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    top.linkTo(title.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            items(characterList.size) { index ->
                MarvelCard(
                    marvelCharacter = characterList[index],
                    onCardClicked = { marvelCharacter -> onItemClicked(marvelCharacter) }
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowCharacters() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        ShowCharacters(listOf(MarvelCharacter(id = 9292, name = "Muriman", description = "Qué es eso?? Es Muriman", img = ""))) { }
    }
}
