package com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pr06_retrofit_albertgarrido_joanlinares.room.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun onSearchTextChange(text: String) {
        _searchedText.value = text
    }

    fun onSearch(text: String) {
        if (text.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertSearchHistory(text)
                loadSearchHistory()
            }
            _searchedText.value = text
        }
    }

    fun selectSearchTerm(text: String) {
        _searchedText.value = text
    }

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearSearchHistory()
            loadSearchHistory()
        }
    }

    fun resetSearch() {
        _searchedText.value = ""
    }

    private fun loadSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val historyEntries = repository.getSearchHistory()
            val historyList = historyEntries.map { it.query }
            _searchHistory.postValue(historyList)
        }
    }
}
