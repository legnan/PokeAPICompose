package com.example.mymvvmrepository.data.repository

import com.example.mymvvmrepository.data.api.ApiService
import com.example.mymvvmrepository.domain.model.Pokemon
import com.example.mymvvmrepository.domain.model.PokemonDetail
import javax.inject.Inject

class PokemonRepository @Inject constructor(
private val api: ApiService
) {
    suspend fun getPokemons(): List<Pokemon> {
        return api.getPokemons().results
    }

    suspend fun getPokemonDetail(id: Int): PokemonDetail {
        return api.getPokemonDetail(id).toDomain()
    }
}

