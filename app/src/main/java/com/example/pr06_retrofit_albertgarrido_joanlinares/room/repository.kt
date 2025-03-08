package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import androidx.lifecycle.LiveData
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.SearchHistoryEntry

class Repository {
    private val daoInterface = PokemonApplication.database.pokemonDao()
    private val searchHistoryDao = PokemonApplication.database.searchHistoryDao()

    suspend fun getAddedToCart(): MutableList<Pokemon> {
        return daoInterface.getAddedToCart()
    }

    suspend fun getTotalCartPrice(): LiveData<Float> {
        return daoInterface.getTotalCartPrice()
    }

    suspend fun findPokemonsByName(text: String): MutableList<Pokemon> {
        return daoInterface.findPokemonsByName(text)
    }

    suspend fun isAddedToCart(name: String): Boolean = daoInterface.isAddedToCart(name)

    suspend fun addPokemonToCart(pokemon: Pokemon) = daoInterface.addPokemonToCart(pokemon)

    suspend fun removePokemonFromCart(pokemon: Pokemon) = daoInterface.removePokemonFromCart(pokemon)

    suspend fun updateAddedToCartStatus(name: String, isAdded: Boolean) =
        daoInterface.updateAddedToCartStatus(name, isAdded)

    // Métodos para Search History
    suspend fun getSearchHistory(): List<SearchHistoryEntry> {
        return searchHistoryDao.getSearchHistory()
    }

    suspend fun insertSearchHistory(query: String) {
        searchHistoryDao.insertSearchEntry(SearchHistoryEntry(query = query))
    }

    suspend fun clearSearchHistory() {
        searchHistoryDao.clearSearchHistory()
    }
}
