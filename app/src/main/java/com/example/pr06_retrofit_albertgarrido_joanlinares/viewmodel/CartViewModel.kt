package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Images
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel : ViewModel() {
    private val repository = Repository()

    // Ahora _cartItems contendrá una lista de Card
    private val _cartItems = MutableLiveData<List<Card>>(emptyList())
    val cartItems: LiveData<List<Card>> get() = _cartItems

    init {
        fetchCartItems()
    }

    private fun fetchCartItems() {
        viewModelScope.launch {
            val items = withContext(Dispatchers.IO) {
                repository.getAddedToCart() // Devuelve MutableList<Pokemon>
            }
            // Filtramos solo los Pokémon marcados y los convertimos a Card
            val cards = items.filter { it.addedToCart }.map { pokemon ->
                Card(
                    id = pokemon.name,               // Usamos el nombre como identificador
                    name = pokemon.name,
                    supertype = null,                // No dispones de este dato, lo dejamos nulo o asigna un valor por defecto
                    subtypes = emptyList(),          // Valor por defecto
                    hp = null,                       // Valor por defecto
                    types = listOf(pokemon.type),    // Convertimos el tipo a una lista
                    images = Images(
                        small = pokemon.image,
                        large = pokemon.image         // Si no dispones de imagen grande, reutilizamos la misma URL
                    )
                )
            }
            _cartItems.postValue(cards)
        }
    }
}
