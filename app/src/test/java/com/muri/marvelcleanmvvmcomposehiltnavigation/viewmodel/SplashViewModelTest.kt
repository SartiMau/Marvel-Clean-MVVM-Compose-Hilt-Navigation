package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.SplashViewModel.SplashData
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.SplashViewModel.SplashState
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
class SplashViewModelTest {

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        viewModel = SplashViewModel()
    }

    @Test
    fun `transforming a Character entity list to a CharacterDB entity list`() {
        runTest(UnconfinedTestDispatcher()) {
            val listOfEmittedResult = mutableListOf<SplashData>()

            val job = viewModel.state.onEach(listOfEmittedResult::add).launchIn(
                CoroutineScope(UnconfinedTestDispatcher(testScheduler))
            )

            viewModel.onAnimationFinish()

            assertEquals(SplashData(SplashState.START_ANIMATION), listOfEmittedResult[0])
            assertEquals(SplashData(SplashState.GO_TO_MAIN_SCREEN), listOfEmittedResult[1])

            job.cancel()
        }
    }
}
