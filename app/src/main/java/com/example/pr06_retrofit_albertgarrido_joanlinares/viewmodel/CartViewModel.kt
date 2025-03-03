package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val repository = Repository()

    private val _cartItems = MutableLiveData<List<Pokemon>>(emptyList())
    val cartItems: LiveData<List<Pokemon>> get() = _cartItems

    init {
        fetchCartItems()
    }

    private fun fetchCartItems() {
        viewModelScope.launch {
            _cartItems.value = repository.getAddedToCart()
        }
    }
}
