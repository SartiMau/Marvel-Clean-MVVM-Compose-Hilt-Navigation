package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterDialogViewModel.MarvelCharacterDialogData
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterDialogViewModel.MarvelCharacterDialogState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MarvelCharacterDialogViewModelTest {

    private lateinit var viewModel: MarvelCharacterDialogViewModel

    @Before
    fun setup() {
        viewModel = MarvelCharacterDialogViewModel()
    }

    @Test
    fun `transforming a Character entity list to a CharacterDB entity list`() {
        runTest(UnconfinedTestDispatcher()) {
            val listOfEmittedResult = mutableListOf<MarvelCharacterDialogData>()

            val job = viewModel.state.onEach(listOfEmittedResult::add).launchIn(
                CoroutineScope(UnconfinedTestDispatcher(testScheduler))
            )

            viewModel.onDismiss()

            assertEquals(MarvelCharacterDialogData(MarvelCharacterDialogState.DRAW), listOfEmittedResult[0])
            assertEquals(MarvelCharacterDialogData(MarvelCharacterDialogState.ON_DIALOG_DISMISSED), listOfEmittedResult[1])

            job.cancel()
        }
    }
}
