package com.muri.marvelcleanmvvmcomposehiltnavigation.navigation

import com.muri.domain.entity.MarvelCharacter
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.AppDestination.MAIN_SCREEN_ROUTE
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.AppDestination.MARVEL_CHARACTER_DETAIL_SCREEN_ROUTE
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.AppDestination.MARVEL_CHARACTER_DIALOG_SCREEN_ROUTE
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.AppDestination.MARVEL_CHARACTER_LIST_SCREEN_ROUTE
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.AppDestination.SPLASH_SCREEN_ROUTE
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.model.toNavMarvelCharacter

object AppDestination {

    const val SPLASH_SCREEN_ROUTE = "splash"
    const val MAIN_SCREEN_ROUTE = "main"
    const val MARVEL_CHARACTER_LIST_SCREEN_ROUTE = "marvelCharacterList"
    const val MARVEL_CHARACTER_DETAIL_SCREEN_ROUTE = "marvelCharacterDetail/{marvelCharacterId}"
    const val MARVEL_CHARACTER_DIALOG_SCREEN_ROUTE = "marvelCharacterDialog/{marvelCharacter}"

    const val MARVEL_CHARACTER_ID_PARAM = "marvelCharacterId"
    const val MARVEL_CHARACTER_PARAM = "marvelCharacter"
}

sealed class AppScreen(val route: String) {
    object Splash : AppScreen(SPLASH_SCREEN_ROUTE)
    object Main : AppScreen(MAIN_SCREEN_ROUTE)
    object MarvelCharacterList : AppScreen(MARVEL_CHARACTER_LIST_SCREEN_ROUTE)
    object MarvelCharacterDetail : AppScreen(MARVEL_CHARACTER_DETAIL_SCREEN_ROUTE) {
        fun createRoute(marvelCharacterId: Int) = "marvelCharacterDetail/$marvelCharacterId"
    }
    object MarvelCharacterDialog : AppScreen(MARVEL_CHARACTER_DIALOG_SCREEN_ROUTE) {
        fun createRoute(marvelCharacter: MarvelCharacter) = "marvelCharacterDialog/${marvelCharacter.toNavMarvelCharacter()}"
    }
}

// sealed class MarvelCharacterScreen(val route: String) {
//    object Adopt : MarvelCharacterScreen("marvelCharacter/{name}/adopt") {
//        fun createRoute(name: String) = "marvelCharacter/$name/adopt"
//    }
//
//    object ContactDetails : MarvelCharacterScreen("marvelCharacter/{name}/contactDetails") {
//        fun createRoute(name: String) = "marvelCharacter/$name/contactDetails"
//    }
// }
