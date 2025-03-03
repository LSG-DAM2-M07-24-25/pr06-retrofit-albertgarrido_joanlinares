package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon

class Repository {
    val daoInterface = PokemonApplication.database.pokemonDao()

    suspend fun getCaptured(): MutableList<Pokemon> = daoInterface.getAddedToCart()
    suspend fun findByName(pokemon: Pokemon) = daoInterface.findByName(pokemon.name).isNotEmpty()
    suspend fun isCaptured(name: String): Boolean = daoInterface.isCaptured(name)
    suspend fun capturePokemon(pokemon: Pokemon) = daoInterface.capturePokemon(pokemon)
    suspend fun freePokemon(pokemon: Pokemon) = daoInterface.freePokemon(pokemon)
    suspend fun updateCapturedStatus(name: String, isCaptured: Boolean) = daoInterface.updateCapturedStatus(name, isCaptured)

}