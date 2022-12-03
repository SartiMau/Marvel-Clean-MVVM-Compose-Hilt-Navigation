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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.muri.marvelcleanmvvmcomposehiltnavigation.R
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.BackgroundImage
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.ContentText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component.TitleText
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.util.OnLifecycleEvent
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onButtonPressed: () -> Unit
) {
    val data: MainViewModel.MainData = viewModel.state.collectAsState().value

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> viewModel.draw()
            Lifecycle.Event.ON_STOP -> viewModel.onStop()
            else -> {}
        }
    }

    when (data.state) {
        MainViewModel.MainState.IDLE -> {}
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
    val constraintSet = ConstraintSet {
        val title = createRefFor(MainScreenID.TITLE_LAYOUT_ID)
        val content = createRefFor(MainScreenID.CONTENT_LAYOUT_ID)
        val button = createRefFor(MainScreenID.BUTTON_LAYOUT_ID)

        constrain(title) {
            top.linkTo(parent.top)
            bottom.linkTo(content.top)
        }

        constrain(content) {
            top.linkTo(title.bottom)
            bottom.linkTo(button.top)
        }

        constrain(button) {
            top.linkTo(content.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        constraintSet = constraintSet
    ) {
        TitleText(
            text = stringResource(R.string.main_screen_title),
            modifier = Modifier
                .layoutId(MainScreenID.TITLE_LAYOUT_ID)
                .fillMaxWidth()

        )
        ContentText(
            text = stringResource(R.string.main_screen_content),
            modifier = Modifier
                .layoutId(MainScreenID.CONTENT_LAYOUT_ID)
                .fillMaxWidth()
        )
        Button(
            onClick = { viewModel.onButtonPressed() },
            modifier = Modifier.layoutId(MainScreenID.BUTTON_LAYOUT_ID),
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

private object MainScreenID {
    const val TITLE_LAYOUT_ID = "TITLE_LAYOUT_ID"
    const val CONTENT_LAYOUT_ID = "CONTENT_LAYOUT_ID"
    const val BUTTON_LAYOUT_ID = "BUTTON_LAYOUT_ID"
}
