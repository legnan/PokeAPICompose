@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mymvvmrepository.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mymvvmrepository.ui.components.ErrorView
import com.example.mymvvmrepository.ui.components.LoadingView
import com.example.mymvvmrepository.ui.components.PokemonItem
import com.example.mymvvmrepository.ui.navigation.AppScreens
import com.example.mymvvmrepository.ui.viewmodel.PokemonListState
import com.example.mymvvmrepository.ui.viewmodel.PokemonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    viewModel: PokemonViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Pokédex",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is PokemonListState.Loading -> LoadingView(modifier = Modifier.align(Alignment.Center))
                is PokemonListState.Success -> {
                    if (state.pokemons.isEmpty()) {
                        EmptyStateView(modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn {
                            itemsIndexed(state.pokemons) { index, pokemon ->
                                PokemonItem(
                                    pokemon = pokemon,
                                    onClick = {
                                        navController.navigate(
                                            AppScreens.PokemonDetail.createRoute(index + 1)
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                is PokemonListState.Error -> ErrorView(
                    message = state.message,
                    onRetry = state.retryAction,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun EmptyStateView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "Lista vacía",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("No se encontraron Pokémon")
    }
}










