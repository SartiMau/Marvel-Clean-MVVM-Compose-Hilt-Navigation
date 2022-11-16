package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.screen.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.AppScreen
import com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.addFeedScreenGraph
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelCleanMVVMComposeHiltNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MarvelApp()
                }
            }
        }
    }
}

@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.Splash.route) {
        addFeedScreenGraph(navController)
//        addMarvelCharacterDetailScreenGraph(navController)
    }
}
