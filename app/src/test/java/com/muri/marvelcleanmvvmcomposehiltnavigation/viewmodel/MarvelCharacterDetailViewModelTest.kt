package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.usecase.GetCharacterUseCase
import com.muri.domain.utils.CoroutineResult
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterDetailViewModel.CharacterDetailData
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterDetailViewModel.CharacterDetailState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MarvelCharacterDetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCharacterUseCase: GetCharacterUseCase

    private var marvelCharacter: MarvelCharacter = MarvelCharacter(ID, NAME, DESCRIPTION, IMG)
    private var exception: Exception = Exception(MSG)

    private lateinit var viewModel: MarvelCharacterDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = MarvelCharacterDetailViewModel(getCharacterUseCase)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `if use case returns success`() {
        runTest(UnconfinedTestDispatcher()) {
            val listOfEmittedResult = mutableListOf<CharacterDetailData>()

            val job = viewModel.state.onEach(listOfEmittedResult::add).launchIn(
                CoroutineScope(UnconfinedTestDispatcher(testScheduler))
            )
            coEvery { getCharacterUseCase(ID) } returns CoroutineResult.Success(marvelCharacter)

            viewModel.getCharacter(ID).join()

            assertEquals(CharacterDetailData(CharacterDetailState.LOADING), listOfEmittedResult[0])
            assertEquals(CharacterDetailData(CharacterDetailState.SHOW_CHARACTER, marvelCharacter), listOfEmittedResult[1])

            job.cancel()
        }
    }

    @Test
    fun `if use case returns error`() {
        runTest(UnconfinedTestDispatcher()) {
            val listOfEmittedResult = mutableListOf<CharacterDetailData>()

            val job = viewModel.state.onEach(listOfEmittedResult::add).launchIn(
                CoroutineScope(UnconfinedTestDispatcher(testScheduler))
            )
            coEvery { getCharacterUseCase(ID) } returns CoroutineResult.Failure(exception)

            viewModel.getCharacter(ID).join()

            assertEquals(CharacterDetailData(state = CharacterDetailState.LOADING), listOfEmittedResult[0])
            assertEquals(CharacterDetailData(state = CharacterDetailState.ERROR, exception = exception), listOfEmittedResult[1])

            job.cancel()
        }
    }

    companion object {
        private const val ID = 1011347
        private const val NAME = "Spider-Man"
        private const val DESCRIPTION = "With great power there must also come great responsibility."
        private const val IMG = "http://terrigen-cdn-dev.marvel.com/content/prod/1x/203ham_com_crd_01.jpg"
        private const val MSG = "ERROR"
    }
}
