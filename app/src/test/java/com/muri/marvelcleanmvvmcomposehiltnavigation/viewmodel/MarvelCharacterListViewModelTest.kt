package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.usecase.GetCharacterListUseCase
import com.muri.domain.utils.CoroutineResult
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterListViewModel.CharacterListData
import com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel.MarvelCharacterListViewModel.CharacterListState
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
class MarvelCharacterListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCharacterListUseCase: GetCharacterListUseCase

    private var marvelCharacterList: List<MarvelCharacter> = listOf(MarvelCharacter(ID, NAME, DESCRIPTION, IMG))
    private var exception: Exception = Exception(MSG)

    private lateinit var viewModel: MarvelCharacterListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = MarvelCharacterListViewModel(getCharacterListUseCase)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `if use case returns success`() {
        runTest(UnconfinedTestDispatcher()) {
            val listOfEmittedResult = mutableListOf<CharacterListData>()

            val job = viewModel.state.onEach(listOfEmittedResult::add).launchIn(
                CoroutineScope(UnconfinedTestDispatcher(testScheduler))
            )
            coEvery { getCharacterListUseCase() } returns CoroutineResult.Success(marvelCharacterList)

            viewModel.getCharacters().join()

            assertEquals(CharacterListData(CharacterListState.LOADING), listOfEmittedResult[0])
            assertEquals(CharacterListData(CharacterListState.SHOW_CHARACTERS, marvelCharacterList), listOfEmittedResult[1])

            job.cancel()
        }
    }

    @Test
    fun `if use case returns error`() {
        runTest(UnconfinedTestDispatcher()) {
            val listOfEmittedResult = mutableListOf<CharacterListData>()

            val job = viewModel.state.onEach(listOfEmittedResult::add).launchIn(
                CoroutineScope(UnconfinedTestDispatcher(testScheduler))
            )
            coEvery { getCharacterListUseCase() } returns CoroutineResult.Failure(exception)

            viewModel.getCharacters().join()

            assertEquals(CharacterListData(state = CharacterListState.LOADING), listOfEmittedResult[0])
            assertEquals(CharacterListData(state = CharacterListState.ERROR, exception = exception), listOfEmittedResult[1])

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
