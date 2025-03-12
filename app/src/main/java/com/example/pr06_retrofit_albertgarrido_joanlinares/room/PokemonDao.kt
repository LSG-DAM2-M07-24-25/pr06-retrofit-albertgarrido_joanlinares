package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemons WHERE is_favorite = 1")
    fun getAddedToCart(): MutableList<Pokemon>

    @Query("SELECT * FROM pokemons WHERE name LIKE '%' || :searchText || '%'")
    fun findPokemonsByName(searchText: String): MutableList<Pokemon>

    // Se consulta por el id proporcionado por la API
    @Query("SELECT is_favorite FROM pokemons WHERE id = :id")
    fun isAddedToCart(id: String): Boolean

    @Insert
    fun addPokemonToCart(pokemon: Pokemon)

    @Delete
    fun removePokemonFromCart(pokemon: Pokemon)

    // Se actualiza el estado utilizando el id de la API
    @Query("UPDATE pokemons SET is_favorite = :isAdded WHERE id = :id")
    fun updateAddedToCartStatus(id: String, isAdded: Boolean)

    @Query("SELECT SUM(averageSellPrice) FROM pokemons WHERE is_favorite = 1")
    fun getTotalCartPrice(): LiveData<Float>
}
