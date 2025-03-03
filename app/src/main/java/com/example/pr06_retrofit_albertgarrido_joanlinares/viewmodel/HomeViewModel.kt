package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.api.CardRepository
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CardRepository = CardRepository()
) : ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> get() = _cards

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        fetchCards()
    }

    private fun fetchCards() {
        viewModelScope.launch {
            try {
                Log.d("HomeViewModel", "Fetching cards from API...")
                val cardsList = repository.getAllCards()

                if (!cardsList.isNullOrEmpty()) {
                    _cards.value = cardsList
                    Log.d("HomeViewModel", "Fetched ${cardsList.size} cards")
                } else {
                    _error.value = "Error al obtener las cartas."
                    Log.e("HomeViewModel", "API returned null or empty list")
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
                Log.e("HomeViewModel", "Exception fetching cards: ${e.message}", e)
            }
        }
    }
}
