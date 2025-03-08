package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchBarViewModel : ViewModel() {
    private val _searchedText = MutableLiveData("")
    val searchedText: LiveData<String> = _searchedText

    private val _searchHistory = MutableLiveData<List<String>>(emptyList())
    val searchHistory: LiveData<List<String>> = _searchHistory

    fun onSearchTextChange(text: String) {
        _searchedText.value = text
    }

    /**
     * Se llama al pulsar el icono de búsqueda o Enter.
     * Agrega el término al historial y lo mantiene para filtrar.
     */
    fun onSearch(text: String) {
        if (text.isNotBlank()) {
            val currentHistory = _searchHistory.value.orEmpty()
            _searchHistory.value = listOf(text) + currentHistory
            _searchedText.value = text
        }
    }

    /**
     * Permite seleccionar un término del historial.
     */
    fun selectSearchTerm(text: String) {
        _searchedText.value = text
    }

    fun clearHistory() {
        _searchHistory.value = emptyList()
    }

    /**
     * Reinicia el término de búsqueda (quita el filtrado).
     */
    fun resetSearch() {
        _searchedText.value = ""
    }
}
