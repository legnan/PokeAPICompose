package com.example.mymvvmrepository.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymvvmrepository.domain.model.Pokemon
import com.example.mymvvmrepository.ui.components.LoadingView
import com.example.mymvvmrepository.ui.components.PokemonItem
import com.example.mymvvmrepository.ui.viewmodel.PokemonListState
import com.example.mymvvmrepository.ui.viewmodel.PokemonViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun PokemonListScreen(
    viewModel: PokemonViewModel,
    onPokemonClick: (Int) -> Unit
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is PokemonListState.Loading -> {
                LoadingView(modifier = Modifier.align(Alignment.Center))
            }

            is PokemonListState.Success -> {
                if (state.pokemons.isEmpty()) {
                    EmptyStateView(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn {
                        items(
                            items = state.pokemons.withIndex().toList(),
                            key = { it }
                        ) { (index, pokemon) ->
                            PokemonItem(
                                pokemon = pokemon,
                                onClick = { onPokemonClick(index) }
                            )
                        }
                    }
                }
            }

            is PokemonListState.Error -> {
                ErrorState(
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

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}








