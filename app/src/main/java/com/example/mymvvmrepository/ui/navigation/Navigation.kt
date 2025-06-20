package com.example.mymvvmrepository.ui.navigation

sealed class AppScreens(val route: String) {
    object PokemonList : AppScreens("pokemonList")
    object PokemonDetail : AppScreens("pokemonDetail/{pokemonId}") {
        fun createRoute(pokemonId: Int) = "pokemonDetail/$pokemonId"
    }
}