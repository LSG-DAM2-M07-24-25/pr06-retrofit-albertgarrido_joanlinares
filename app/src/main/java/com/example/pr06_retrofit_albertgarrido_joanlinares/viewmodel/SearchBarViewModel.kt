package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de gestionar la barra de búsqueda y su historial asociado.
 * Permite la búsqueda, actualización y almacenamiento del historial de búsqueda.
 */
class SearchBarViewModel : ViewModel() {
    private val _searchedText = MutableLiveData("")
    val searchedText: LiveData<String> = _searchedText

    // Almacena el historial cargado de la BD
    private val _searchHistory = MutableLiveData<List<String>>(emptyList())
    val searchHistory: LiveData<List<String>> = _searchHistory

    private val repository = Repository()

    init {
        loadSearchHistory()
    }

    /**
     * Actualiza el texto actual introducido por el usuario en la barra de búsqueda.
     *
     * @param text Texto actual introducido por el usuario.
     */
    fun onSearchTextChange(text: String) {
        _searchedText.value = text
    }

    /**
     * Ejecuta una búsqueda y añade el término buscado al historial si no está vacío.
     *
     * @param text Término introducido por el usuario para realizar la búsqueda.
     */
    fun onSearch(text: String) {
        if (text.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertSearchHistory(text)
                loadSearchHistory()
            }
            _searchedText.value = text
        }
    }

    /**
     * Selecciona un término previamente buscado del historial.
     *
     * @param text Texto seleccionado desde el historial de búsqueda.
     */
    fun selectSearchTerm(text: String) {
        _searchedText.value = text
    }

    /**
     * Limpia el historial de búsqueda almacenado localmente.
     */
    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearSearchHistory()
            loadSearchHistory()
        }
    }

    /**
     * Restablece el campo de búsqueda al estado inicial vacío.
     */
    fun resetSearch() {
        _searchedText.value = ""
    }

    /**
     * Carga el historial de búsqueda desde la base de datos local al estado del ViewModel.
     */
    private fun loadSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val historyEntries = repository.getSearchHistory()
            val historyList = historyEntries.map { it.query }
            _searchHistory.postValue(historyList)
        }
    }
}
