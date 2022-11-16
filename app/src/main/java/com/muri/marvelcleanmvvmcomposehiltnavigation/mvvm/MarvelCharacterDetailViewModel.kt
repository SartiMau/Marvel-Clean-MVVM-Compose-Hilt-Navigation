package com.muri.marvelcleanmvvmcomposehiltnavigation.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.usecase.GetCharacterUseCase
import com.muri.domain.utils.CoroutineResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MarvelCharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private var mutableState = MutableStateFlow(Data(State.LOADING))
    val state: StateFlow<Data> = mutableState

    fun getCharacters(characterId: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) { getCharacterUseCase(characterId) }.let { result ->
            when (result) {
                is CoroutineResult.Success -> {
                    mutableState.value = mutableState.value.copy(
                        state = State.SHOW_CHARACTER,
                        marvelCharacter = result.data
                    )
                }
                is CoroutineResult.Failure -> {
                    mutableState.value = mutableState.value.copy(state = State.ERROR, exception = result.exception)
                }
            }
        }
    }

    data class Data(
        val state: State,
        val marvelCharacter: MarvelCharacter? = null,
        val exception: Exception? = null
    )

    enum class State {
        LOADING,
        SHOW_CHARACTER,
        ERROR
    }
}
