package com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.SearchBarViewModel

/**
 * Composable que representa una barra de búsqueda con historial y navegación integrada.
 *
 * Este componente permite a los usuarios buscar elementos, ver un historial de búsquedas previas
 * y navegar a la pantalla de búsqueda avanzada cuando está en modo inactivo.
 *
 * - En **modo activo**, muestra un campo de búsqueda interactivo con opciones de autocompletado e historial.
 * - En **modo inactivo**, permite navegar a una vista de búsqueda más completa.
 *
 * @param searchBarViewModel ViewModel encargado de gestionar la lógica de búsqueda y el historial.
 * @param navigationController Controlador de navegación para gestionar el acceso a la pantalla de búsqueda avanzada.
 * @param onSearchComplete Acción que se ejecuta al completar una búsqueda (normalmente para actualizar la vista principal).
 * @param active Indica si la barra de búsqueda está activa o inactiva.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchBarViewModel: SearchBarViewModel,
    navigationController: NavHostController,
    onSearchComplete: () -> Unit,
    active: Boolean
) {
    val searchedText by searchBarViewModel.searchedText.observeAsState("")
    val historyList by searchBarViewModel.searchHistory.observeAsState(emptyList())

    if (active) {
        // Modo Activo
        SearchBar(
            query = searchedText,
            onQueryChange = { text -> searchBarViewModel.onSearchTextChange(text) },
            onSearch = { text ->
                searchBarViewModel.onSearch(text)
                onSearchComplete() // Vuelve a homeview con filtrados
            },
            active = true,
            onActiveChange = { },
            leadingIcon = {
                // luba interactiva
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    modifier = Modifier.clickable {
                        searchBarViewModel.onSearch(searchedText)
                        onSearchComplete()
                    }
                )
            },
            trailingIcon = {
                if (historyList.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Limpiar historial",
                        tint = Color.Red,
                        modifier = Modifier.clickable { searchBarViewModel.clearHistory() }
                    )
                }
            },
            placeholder = { Text("Buscar...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            // historial
            if (historyList.isNotEmpty()) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(historyList) { textItem ->
                            Text(
                                text = textItem,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        searchBarViewModel.selectSearchTerm(textItem)
                                        searchBarViewModel.onSearch(textItem)
                                        onSearchComplete()
                                    }
                            )
                        }
                    }
                }
            }
        }
    } else {
        // Inactivo, nav to search view
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { searchBarViewModel.resetSearch() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Resetear búsqueda"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF0F0F0))
                    .clickable {
                        navigationController.navigate(Routes.Screen4.route)
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                if (searchedText.isEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Buscar",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Buscar...", color = Color.Gray)
                    }
                } else {
                    Text(text = searchedText, color = Color.Black)
                }
            }
        }
    }
}
