package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MainViewModel.MainData
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MainViewModel.MainState
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
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun `transforming a Character entity list to a CharacterDB entity list`() {
        runTest(UnconfinedTestDispatcher()) {
            val listOfEmittedResult = mutableListOf<MainData>()

            val job = viewModel.state.onEach(listOfEmittedResult::add).launchIn(
                CoroutineScope(UnconfinedTestDispatcher(testScheduler))
            )

            viewModel.onButtonPressed()

            assertEquals(MainData(MainState.IDLE), listOfEmittedResult[0])
            assertEquals(MainData(MainState.GO_TO_CHARACTER_LIST), listOfEmittedResult[1])

            job.cancel()
        }
    }
}
