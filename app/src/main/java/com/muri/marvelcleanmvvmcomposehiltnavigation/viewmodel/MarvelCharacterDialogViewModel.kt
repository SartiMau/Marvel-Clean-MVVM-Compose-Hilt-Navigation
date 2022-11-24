package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MarvelCharacterDialogViewModel @Inject constructor() : ViewModel() {

    private var mutableState = MutableStateFlow(MarvelCharacterDialogData(MarvelCharacterDialogState.DRAW))
    val state: StateFlow<MarvelCharacterDialogData> = mutableState

    fun onDismiss() {
        mutableState.value = mutableState.value.copy(
            state = MarvelCharacterDialogState.ON_DIALOG_DISMISSED
        )
    }

    data class MarvelCharacterDialogData(
        val state: MarvelCharacterDialogState
    )

    enum class MarvelCharacterDialogState {
        DRAW,
        ON_DIALOG_DISMISSED
    }
}
