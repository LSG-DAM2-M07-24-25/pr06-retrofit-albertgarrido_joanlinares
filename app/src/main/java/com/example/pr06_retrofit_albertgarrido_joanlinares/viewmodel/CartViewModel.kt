package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel : ViewModel() {
    private val repository = Repository()

    private val _cartItems = MutableLiveData<List<Pokemon>>(emptyList())
    val cartItems: LiveData<List<Pokemon>> get() = _cartItems

    init {
        fetchCartItems()
    }

    private fun fetchCartItems() {
        viewModelScope.launch {
            // Llamamos a getAddedToCart() dentro de Dispatchers.IO para no bloquear la UI
            val items = withContext(Dispatchers.IO) {
                repository.getAddedToCart() // Devuelve MutableList<Pokemon>
            }
            // Actualizamos el LiveData en el hilo principal
            _cartItems.value = items
        }
    }
}

