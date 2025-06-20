package com.example.mymvvmrepository.data.repository

import com.example.mymvvmrepository.domain.model.Pokemon
import com.example.mymvvmrepository.domain.model.PokemonDetail

interface PokemonRepository  {
    suspend fun getPokemons(): Result<List<Pokemon>>
    suspend fun getPokemonDetail(id: Int):  Result<PokemonDetail>
}

