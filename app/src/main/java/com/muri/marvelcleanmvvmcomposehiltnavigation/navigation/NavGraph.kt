package com.muri.marvelcleanmvvmcomposehiltnavigation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.AppDestination.MARVEL_CHARACTER_ID_PARAM
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.AppDestination.MARVEL_CHARACTER_PARAM
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.model.MarvelCharacterArgType
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.model.NavMarvelCharacter
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.model.toMarvelCharacter
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MainScreen
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDetailScreen
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterDialogScreen
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.MarvelCharacterListScreen
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.SplashScreen

fun NavGraphBuilder.addFeedScreenGraph(navController: NavController) {
    composable(route = AppScreen.Splash.route) {
        SplashScreen {
            navController.popBackStack()
            navController.navigate(AppScreen.Main.route)
        }
    }
    composable(route = AppScreen.Main.route) {
        MainScreen { navController.navigate(AppScreen.MarvelCharacterList.route) }
    }
    composable(route = AppScreen.MarvelCharacterList.route) {
        MarvelCharacterListScreen(
            onItemClicked = { marvelCharacter -> navController.navigate(AppScreen.MarvelCharacterDetail.createRoute(marvelCharacter.id)) },
            onError = { navController.popBackStack() }
        )
    }
    // Ejemplo para ver como pasar algo por param que no sea string..
    // si es string no hay que definir el arguments
    composable(
        route = AppScreen.MarvelCharacterDetail.route,
        arguments = listOf(
            navArgument(MARVEL_CHARACTER_ID_PARAM) { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val marvelCharacterId = backStackEntry.arguments?.getInt(MARVEL_CHARACTER_ID_PARAM)
        requireNotNull(marvelCharacterId)
        MarvelCharacterDetailScreen(
            marvelCharacterId = marvelCharacterId,
            onImageClicked = { marvelCharacter -> navController.navigate(AppScreen.MarvelCharacterDialog.createRoute(marvelCharacter)) },
            onError = { navController.popBackStack() }
        )
    }
    // Esto lo hice solamente como ejemplo para ver como pasar un objecto por param
    composable(
        route = AppScreen.MarvelCharacterDialog.route,
        arguments = listOf(
            navArgument(MARVEL_CHARACTER_PARAM) { type = MarvelCharacterArgType() }
        )
    ) { backStackEntry ->
        val navMarvelCharacter =
            backStackEntry.arguments?.getString(MARVEL_CHARACTER_PARAM)?.let { Gson().fromJson(it, NavMarvelCharacter::class.java) }
        requireNotNull(navMarvelCharacter)
        MarvelCharacterDialogScreen(
            navMarvelCharacter.toMarvelCharacter(),
            onDismiss = { navController.popBackStack() }
        )
    }
}

// Esto esta bueno para ver como mandar a otro NavGraph
// fun NavGraphBuilder.addMarvelCharacterDetailScreenGraph(navController: NavController) {
//    navigation(
//        route = AppScreen.MarvelCharacterDetail.route,
//        startDestination = MarvelCharacterScreen.Adopt.route
//    ) {
//        composable(route = MarvelCharacterScreen.Adopt.route) {
//            val characterName = it.arguments?.getString("name").orEmpty()
//            MarvelCharacterDetailScreen(
//                characterName,
//                navigateUp = { navController.popBackStack(MarvelCharacterScreen.Adopt.createRoute(characterName), inclusive = true) }
//            )
//        }
//        composable(route = MarvelCharacterScreen.ContactDetails.route) { backStackEntry ->
//            val characterName = backStackEntry.arguments?.getString("name").orEmpty()
//            AdoptionContactDetailsScreen(characterName)
//        }
//    }
// }
