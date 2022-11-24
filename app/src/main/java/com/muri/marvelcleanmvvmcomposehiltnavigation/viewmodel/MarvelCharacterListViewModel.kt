package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.usecase.GetCharacterListUseCase
import com.muri.domain.utils.CoroutineResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MarvelCharacterListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    private var mutableState = MutableStateFlow(CharacterListData(CharacterListState.LOADING))
    val state: StateFlow<CharacterListData> = mutableState

    fun getCharacters() = viewModelScope.launch {
        withContext(Dispatchers.IO) { getCharacterListUseCase() }.let { result ->
            when (result) {
                is CoroutineResult.Success -> {
                    mutableState.value = mutableState.value.copy(
                        state = CharacterListState.SHOW_CHARACTERS,
                        characterList = result.data
                    )
                }
                is CoroutineResult.Failure -> {
                    mutableState.value = mutableState.value.copy(state = CharacterListState.ERROR, exception = result.exception)
                }
            }
        }
    }

    data class CharacterListData(
        val state: CharacterListState,
        val characterList: List<MarvelCharacter> = emptyList(),
        val exception: Exception? = null
    )

    enum class CharacterListState {
        LOADING,
        SHOW_CHARACTERS,
        ERROR
    }
}
