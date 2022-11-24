package com.muri.marvelcleanmvvmcomposehiltnavigation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private var mutableState = MutableStateFlow(SplashData(SplashState.START_ANIMATION))
    val state: StateFlow<SplashData> = mutableState

    fun onAnimationFinish() {
        mutableState.value = mutableState.value.copy(
            state = SplashState.GO_TO_MAIN_SCREEN
        )
    }

    data class SplashData(
        val state: SplashState
    )

    enum class SplashState {
        START_ANIMATION,
        GO_TO_MAIN_SCREEN
    }
}
