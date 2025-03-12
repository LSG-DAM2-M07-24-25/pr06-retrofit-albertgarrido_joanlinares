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

    suspend fun isCardInCart(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            cartRepository.isAddedToCart(id)
        }
    }

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
