package com.example.mymvvmrepository.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Float?,
    val weight: Float?
)