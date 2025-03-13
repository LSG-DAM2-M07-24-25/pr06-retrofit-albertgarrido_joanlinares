package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.api.CardRepository
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util.CartRefresh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository as RoomRepository
/**
 * ViewModel encargado de gestionar las cartas obtenidas desde la API Pokémon TCG,
 * gestionar la selección individual de cartas y manejar el estado de añadido o eliminado al carrito.
 */
class HomeViewModel(
    private val repository: CardRepository = CardRepository()
) : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>(emptyList())
    val cards: LiveData<List<Card>> get() = _cards

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _selectedCard = MutableLiveData<Card?>()
    val selectedCard: LiveData<Card?> get() = _selectedCard

    private val cartRepository = RoomRepository()

    init {
        fetchCards()
    }
    /**
     * Realiza la llamada asíncrona a la API mediante Retrofit para obtener todas las cartas Pokémon.
     * Actualiza LiveData con la lista obtenida o maneja errores informando al usuario.
     */
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

    /**
     * Selecciona una carta específica y actualiza el estado para mostrar sus detalles.
     *
     * @param card Carta que el usuario ha seleccionado.
     */
    fun selectCard(card: Card) {
        _selectedCard.value = card
    }

    /**
     * Comprueba de forma asíncrona si una carta específica se encuentra añadida en el carrito local.
     *
     * @param id Identificador único de la carta Pokémon.
     * @return Boolean indicando si la carta está añadida al carrito.
     */
    suspend fun isCardInCart(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            cartRepository.isAddedToCart(id)
        }
    }

    /**
     * Cambia el estado de una carta en el carrito. Añade o elimina la carta del almacenamiento local
     * según corresponda, notificando además a la interfaz para que actualice los datos visualizados.
     *
     * @param card Carta seleccionada por el usuario.
     * @param isAdded Indica si la carta debe añadirse (true) o eliminarse (false) del carrito.
     */
    fun toggleCartStatus(card: Card, isAdded: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pokemon = card.toPokemon(isAdded)
                val exists = cartRepository.isAddedToCart(pokemon.id)

                if (isAdded) {
                    if (!exists) {
                        cartRepository.addPokemonToCart(pokemon)
                    }
                } else {
                    if (exists) {
                        cartRepository.removePokemonFromCart(pokemon)
                    }
                }

                cartRepository.updateAddedToCartStatus(pokemon.id, isAdded)
                CartRefresh.refreshTrigger.postValue(true)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Convierte un objeto de tipo Card a un objeto Pokemon, adecuado para almacenar en la base de datos local.
     *
     * @param isAdded Boolean indicando el estado actual de añadido al carrito.
     * @return Instancia del objeto Pokemon generado a partir de la carta.
     */
    private fun Card.toPokemon(isAdded: Boolean): Pokemon {
        return Pokemon(
            id = this.id,
            name = this.name,
            type = this.types?.joinToString(", ") ?: this.supertype ?: "Desconocido",
            image = this.images.large,
            addedToCart = isAdded,
            averageSellPrice = this.cardmarket?.prices?.averageSellPrice ?: 0.0
        )
    }
}
