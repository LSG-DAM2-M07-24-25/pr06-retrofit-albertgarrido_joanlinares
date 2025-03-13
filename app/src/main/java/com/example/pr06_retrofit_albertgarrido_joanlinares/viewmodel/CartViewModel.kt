package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.CardMarket
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.CardMarketPrices
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Images
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util.CartRefresh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel que gestiona los elementos del carrito de compras y su precio total.
 */
class CartViewModel : ViewModel() {
    private val repository = Repository()

    private val _cartItems = MutableLiveData<List<Card>>()
    val cartItems: LiveData<List<Card>> get() = _cartItems

    private val _totalPrice = MutableLiveData<Float>()
    val totalPrice: LiveData<Float> get() = _totalPrice

    init {
        fetchCartItems()
        fetchTotalPrice()

        // Observa el trigger para refrescar datos del carrito.
        CartRefresh.refreshTrigger.observeForever { refresh ->
            if (refresh == true) {
                fetchCartItems()
                fetchTotalPrice()
                // Reinicia el trigger
                CartRefresh.refreshTrigger.postValue(false)
            }
        }
    }

    /**
     * Obtiene las cartas añadidas al carrito desde la base de datos local
     * y las transforma al formato Card para su presentación en la vista.
     */
    private fun fetchCartItems() {
        viewModelScope.launch {
            val pokemons: MutableList<Pokemon> = withContext(Dispatchers.IO) {
                repository.getAddedToCart()
            }
            val cards = pokemons.map { pokemon ->
                Card(
                    id = pokemon.id,
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
                    )
                )
            }
            _cartItems.postValue(cards)
        }
    }
    /**
     * Obtiene el precio total de todos los elementos presentes en el carrito de compras.
     */
    private fun fetchTotalPrice() {
        viewModelScope.launch {
            val totalPriceLiveData: LiveData<Float> = withContext(Dispatchers.IO) {
                repository.getTotalCartPrice()
            }
            totalPriceLiveData.observeForever { total ->
                _totalPrice.postValue(total ?: 0f)
            }
        }
    }
}
