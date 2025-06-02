package com.example.mymvvmrepository.data.model

import com.example.mymvvmrepository.domain.model.Pokemon
import com.example.mymvvmrepository.domain.model.PokemonDetail

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
) {

}