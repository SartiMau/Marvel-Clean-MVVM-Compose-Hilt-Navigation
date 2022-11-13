package com.muri.marvelcleanmvvmcomposehiltnavigation.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.usecase.GetCharacterListUseCase
import com.muri.domain.utils.CoroutineResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    private val _characterState: MutableLiveData<CharactersData> = MutableLiveData()
    val characterState: LiveData<CharactersData> get() = _characterState

    fun getCharacters() = viewModelScope.launch {
        withContext(Dispatchers.IO) { getCharacterListUseCase() }.let { result ->
            when (result) {
                is CoroutineResult.Success -> {
                    _characterState.postValue(
                        CharactersData(
                            characterState = CharactersState.SHOW_CHARACTERS,
                            characterInformation = result.data
                        )
                    )
                }
                is CoroutineResult.Failure -> {
                }
            }
        }
    }

    data class CharactersData(
        val characterState: CharactersState,
        val characterInformation: List<MarvelCharacter> = emptyList()
    )

    enum class CharactersState {
        SHOW_CHARACTERS
    }
}
