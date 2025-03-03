package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.api.CardRepository
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import kotlinx.coroutines.Dispatchers
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository as RoomRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: CardRepository = CardRepository()
) : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>(emptyList())
    val cards: LiveData<List<Card>> get() = _cards

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // LiveData para la carta seleccionada
    private val _selectedCard = MutableLiveData<Card?>()
    val selectedCard: LiveData<Card?> get() = _selectedCard

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
                    _cards.value = cardsList!!
                } else {
                    _error.value = "Error al obtener las cartas."
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }

    fun selectCard(card: Card) {
        _selectedCard.value = card
    }

    /**
     * Comprueba en la base de datos si la carta (por nombre) está en el carrito.
     * Se ejecuta en Dispatchers.IO para no bloquear la UI.
     */
    suspend fun isCardInCart(name: String): Boolean {
        return withContext(Dispatchers.IO) {
            cartRepository.isAddedToCart(name)
        }
    }

    /**
     * Alterna el estado en Room (añade o quita la carta del carrito),
     * sin actualizar un LiveData de boolean, sino que devuelves esa
     * responsabilidad a la UI (CardDetails).
     */
    fun toggleCartStatus(card: Card, isAdded: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pokemon = card.toPokemon(isAdded)
                if (isAdded) {
                    cartRepository.addPokemonToCart(pokemon)
                } else {
                    cartRepository.removePokemonFromCart(pokemon)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Función de extensión para convertir Card en Pokemon (con la URL de la imagen)
    fun Card.toPokemon(isAdded: Boolean): Pokemon {
        return Pokemon(
            name = this.name,
            type = this.types?.joinToString(", ") ?: this.supertype ?: "Desconocido",
            image = this.images.large, // Es una URL, no un recurso local
            addedToCart = isAdded
        )
    }
}
