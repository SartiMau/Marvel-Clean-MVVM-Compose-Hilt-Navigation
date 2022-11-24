package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.muri.marvelcleanmvvmcomposehiltnavigation.R
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.BackgroundImage
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.ContentText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.TitleText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onButtonPressed: () -> Unit
) {
    val data: MainViewModel.MainData = viewModel.state.collectAsState().value

    when (data.state) {
        MainViewModel.MainState.DRAW -> DrawScreen(viewModel)
        MainViewModel.MainState.GO_TO_CHARACTER_LIST -> {
            LaunchedEffect(Unit) {
                onButtonPressed()
            }
        }
    }
}

@Composable
private fun DrawScreen(viewModel: MainViewModel) {
    BackgroundImage()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        val (title, content, button) = createRefs()
        createVerticalChain(title, content, button)

        TitleText(
            text = stringResource(R.string.main_screen_title),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(content.top)
                }
        )
        ContentText(
            text = stringResource(R.string.main_screen_content),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(content) {
                    top.linkTo(title.bottom)
                    bottom.linkTo(button.top)
                }
        )
        Button(
            onClick = { viewModel.onButtonPressed() },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(content.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.Red
            )
        ) {
            Text(
                text = stringResource(R.string.main_screen_button),
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        MainScreen { }
    }
}
