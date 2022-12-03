package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var mutableState = MutableStateFlow(MainData(MainState.IDLE))
    val state: StateFlow<MainData> = mutableState

    fun draw() {
        mutableState.value = mutableState.value.copy(
            state = MainState.DRAW
        )
    }

    fun onButtonPressed() {
        mutableState.value = mutableState.value.copy(
            state = MainState.GO_TO_CHARACTER_LIST
        )
    }

    fun onStop() {
        mutableState.value = mutableState.value.copy(
            state = MainState.IDLE
        )
    }

    data class MainData(
        val state: MainState
    )

    enum class MainState {
        IDLE,
        DRAW,
        GO_TO_CHARACTER_LIST
    }
}
