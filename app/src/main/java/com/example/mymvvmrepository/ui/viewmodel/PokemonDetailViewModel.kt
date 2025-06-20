package com.example.mymvvmrepository.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymvvmrepository.data.repository.PokemonRepositoryImpl
import com.example.mymvvmrepository.domain.model.PokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor( savedStateHandle: SavedStateHandle,
                                                 private val pokemonRepository: PokemonRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    val state: StateFlow<PokemonDetailState> = _state

    val pokemonId = savedStateHandle.get<String>("pokemonId")?.toIntOrNull() ?: 0

    init {
        loadPokemon(pokemonId)
    }


    fun loadPokemon(id: Int) {
        viewModelScope.launch {
            _state.value = PokemonDetailState.Loading
            pokemonRepository.getPokemonDetail(id).fold(
                onSuccess = { pokemon ->
                    _state.value = PokemonDetailState.Success(pokemon)
                },
                onFailure = { error ->
                    _state.value = PokemonDetailState.Error(
                        message = "Error: ${error.localizedMessage ?: "Desconocido"}",
                        retryAction = { loadPokemon(id) }
                    )
                }
            )
        }
    }
}

sealed interface PokemonDetailState {
    data object Loading : PokemonDetailState
    data class Success(val pokemon: PokemonDetail) : PokemonDetailState
    data class Error(val message: String, val retryAction: () -> Unit) : PokemonDetailState
}