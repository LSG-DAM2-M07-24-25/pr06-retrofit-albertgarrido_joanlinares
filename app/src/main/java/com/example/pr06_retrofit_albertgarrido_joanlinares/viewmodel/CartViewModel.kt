package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Images
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
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
                    )
                )
            }
        }
    }

    /**
     * Función de extensión para convertir un objeto Card en Pokemon,
     * que podría usarse para actualizar el estado en la base de datos.
     */
    private fun Card.toPokemon(isAdded: Boolean): Pokemon {
        return Pokemon(
            name = this.name,
            type = this.types!!.joinToString(", "),
            image = this.images.large,
            addedToCart = isAdded
        )
    }
}
