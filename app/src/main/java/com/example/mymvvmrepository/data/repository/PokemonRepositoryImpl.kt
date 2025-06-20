package com.example.mymvvmrepository.data.repository

import com.example.mymvvmrepository.data.remote.ApiService
import com.example.mymvvmrepository.domain.model.Pokemon
import com.example.mymvvmrepository.domain.model.PokemonDetail
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val apiService: ApiService): PokemonRepository {
    override suspend fun getPokemons(): Result<List<Pokemon>> {
        return try {
            val response = apiService.getPokemons()
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getPokemonDetail(id: Int): Result<PokemonDetail> {
        return try {
            val response = apiService.getPokemonDetail(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}