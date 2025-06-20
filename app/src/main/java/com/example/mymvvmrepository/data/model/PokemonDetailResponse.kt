package com.example.mymvvmrepository.data.model

import com.example.mymvvmrepository.domain.model.PokemonDetail


data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int
) {

    fun toDomain(): PokemonDetail {
        return PokemonDetail(
            id = id,
            name = name.replaceFirstChar { it.uppercase() },
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
            height = height / 10f,
            weight = weight / 10f
        )
    }
}


