package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.CardMarket
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.CardMarketPrices
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Images
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository

class CartViewModel : ViewModel() {
    private val repository = Repository()

    // MediatorLiveData nos permite observar los cambios en LiveData y transformarlos
    private val _cartItems = MediatorLiveData<List<Card>>()
    val cartItems: LiveData<List<Card>> get() = _cartItems

    init {
        _cartItems.addSource(repository.getAddedToCart()) { pokemons ->
            _cartItems.value = pokemons.filter { it.addedToCart }.map { pokemon ->
                Card(
                    id = pokemon.name,
                    name = pokemon.name,
                    supertype = null,
                    subtypes = emptyList(),
                    hp = null,
                    types = listOf(pokemon.type),
                    images = Images(
                        small = pokemon.image,
                        large = pokemon.image
                    ),
                    cardmarket = CardMarket(
                        url = null,
                        updatedAt = null,
                        prices = CardMarketPrices(
                            averageSellPrice = pokemon.averageSellPrice
                        )
                    ),
                    tcgplayer = null
                )
            }
        }
    }
}
