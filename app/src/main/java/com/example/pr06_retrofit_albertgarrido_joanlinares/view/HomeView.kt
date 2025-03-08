package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util.CardList
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util.SearchBar
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.HomeViewModel
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.SearchBarViewModel

@Composable
fun HomeView(
    navigationController: NavHostController,
    homeViewModel: HomeViewModel,
    searchBarViewModel: SearchBarViewModel
) {
    val cards by homeViewModel.cards.observeAsState(emptyList())
    val error by homeViewModel.error.observeAsState()

    // Filtrado local
    val searchText by searchBarViewModel.searchedText.observeAsState("")
    val filteredCards = if (searchText.isEmpty()) cards else cards.filter { it.name.contains(searchText, ignoreCase = true) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = maxWidth
        val paddingValue = when {
            screenWidth < 600.dp -> 8.dp
            screenWidth < 840.dp -> 16.dp
            else -> 24.dp
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            // Botón para ir a la pantalla de Carrito
            item {
                Button(
                    modifier = Modifier
                        .padding(paddingValue)
                        .padding(top = 20.dp),
                    onClick = { navigationController.navigate(Routes.Screen3.route) }
                ) {
                    Text(text = "Carrito")
                }
            }
            item {
                SearchBar(
                    searchBarViewModel = searchBarViewModel,
                    navigationController = navigationController,
                    onSearchComplete = { },
                    active = false
                )
            }
            // Cartas Filtradas
            item {
                if (error != null) {
                    Text(
                        text = error ?: "",
                        modifier = Modifier.padding(paddingValue)
                    )
                } else {
                    CardList(
                        items = filteredCards,
                        imageProvider = { card -> card.images.small },
                        nameProvider = { card -> card.name },
                        onCardClick = { card ->
                            homeViewModel.selectCard(card)
                            navigationController.navigate(Routes.Screen2.route)
                        }
                    )
                }
            }
        }
    }
}

