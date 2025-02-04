package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.api.CardRepository
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card
import kotlinx.coroutines.launch

class CardListViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>?>()
    val cards: MutableLiveData<List<Card>?> get() = _cards

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val repository = CardRepository()

    init {
        fetchCards()
    }

    private fun fetchCards() {
        viewModelScope.launch {
            try {
                val cardList = repository.getAllCards()
                if (cardList != null) {
                    _cards.value = cardList
                } else {
                    _error.value = "Error al obtener las cartas."
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }
}
