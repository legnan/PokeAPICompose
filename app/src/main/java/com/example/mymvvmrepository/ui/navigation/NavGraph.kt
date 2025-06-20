package com.example.mymvvmrepository.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymvvmrepository.ui.screens.PokemonDetailScreen
import com.example.mymvvmrepository.ui.screens.PokemonListScreen


@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.PokemonList.route
    ) {
        composable(AppScreens.PokemonList.route) {
            PokemonListScreen(navController = navController)
        }

        composable(AppScreens.PokemonDetail.route) {
            PokemonDetailScreen(navController = navController)
        }
    }
}