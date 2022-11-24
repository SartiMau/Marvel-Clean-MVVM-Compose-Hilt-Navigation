package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var mutableState = MutableStateFlow(MainData(MainState.DRAW))
    val state: StateFlow<MainData> = mutableState

    fun onButtonPressed() {
        mutableState.value = mutableState.value.copy(
            state = MainState.GO_TO_CHARACTER_LIST
        )
    }

    data class MainData(
        val state: MainState
    )

    enum class MainState {
        DRAW,
        GO_TO_CHARACTER_LIST
    }
}
