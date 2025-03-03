package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.api.CardRepository
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository as RoomRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CardRepository = CardRepository()
) : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>(emptyList())
    val cards: LiveData<List<Card>> get() = _cards

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // LiveData para la carta seleccionada
    val selectedCard = MutableLiveData<Card?>()

    // Repository para operaciones en Room (carrito)
    private val cartRepository = RoomRepository()

    init {
        fetchCards()
    }

    private fun fetchCards() {
        viewModelScope.launch {
            try {
                val cardsList = repository.getAllCards()
                if (!cardsList.isNullOrEmpty()) {
                    _cards.value = cardsList
                } else {
                    _error.value = "Error al obtener las cartas."
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }

    fun selectCard(card: Card) {
        selectedCard.value = card
    }

    // Función para actualizar el estado "addedToCart"
    fun toggleCartStatus(card: Card, isAdded: Boolean) {
        viewModelScope.launch {
            // Convertimos la Card a una entidad Pokemon.
            // Reemplaza 0 por el recurso de imagen real (por ejemplo, R.drawable.ic_pokemon)
            val pokemon = card.toPokemon(isAdded)
            if (isAdded) {
                cartRepository.addPokemonToCart(pokemon)
            } else {
                cartRepository.removePokemonFromCart(pokemon)
            }
        }
    }
}

// Función de extensión para convertir Card en Pokemon
fun Card.toPokemon(isAdded: Boolean): Pokemon {
    return Pokemon(
        name = this.name,
        type = this.types?.joinToString(", ") ?: this.supertype ?: "Desconocido",
        image = 0, // Cambia 0 por el recurso de imagen adecuado, e.g. R.drawable.ic_pokemon
        addedToCart = isAdded
    )
}
