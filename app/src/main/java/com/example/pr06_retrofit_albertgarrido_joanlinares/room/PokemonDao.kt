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

    @Query("SELECT * FROM pokemons WHERE name = :name")
    fun findByName(name: String): MutableList<Pokemon>

    @Query("SELECT is_favorite FROM pokemons WHERE name = :name")
    fun isAddedToCart(name: String): Boolean

    @Insert
    fun addPokemonToCart(pokemon: Pokemon)

    @Delete
    fun removePokemonFromCart(pokemon: Pokemon)

    @Query("UPDATE pokemons SET is_favorite = :isAdded WHERE name = :name")
    fun updateAddedToCartStatus(name: String, isAdded: Boolean)

    @Query("SELECT SUM(averageSellPrice) FROM pokemons WHERE is_favorite = 1")
    fun getTotalCartPrice(): LiveData<Float>
}
