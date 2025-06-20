package com.example.mymvvmrepository.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymvvmrepository.data.repository.PokemonRepositoryImpl
import com.example.mymvvmrepository.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow<PokemonListState>(PokemonListState.Loading)
    val state: StateFlow<PokemonListState> = _state

    init {
        loadPokemons()
    }

    fun loadPokemons() {
        viewModelScope.launch {
            _state.value = PokemonListState.Loading

            pokemonRepository.getPokemons().fold(
                onSuccess = { pokemons ->
                    _state.value = PokemonListState.Success(pokemons)
                },
                onFailure = { error ->
                    _state.value = PokemonListState.Error(
                        message = "Error: ${error.localizedMessage ?: "Desconocido"}",
                        retryAction = { loadPokemons() }
                    )
                }
            )
        }
    }
}

sealed interface PokemonListState {
    data object Loading : PokemonListState
    data class Success(val pokemons: List<Pokemon>) : PokemonListState
    data class Error(val message: String, val retryAction: () -> Unit) : PokemonListState
}