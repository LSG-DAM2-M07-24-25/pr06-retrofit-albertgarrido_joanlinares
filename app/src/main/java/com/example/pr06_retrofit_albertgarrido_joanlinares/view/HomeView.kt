package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.card.CardList
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.HomeViewModel


@Composable
fun HomeView(navigationController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel()
    val cards by homeViewModel.cards.collectAsState()
    val error by homeViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Pokémon Cards", modifier = Modifier.padding(bottom = 16.dp)
        )

        // Si hay error, se muestra un mensaje; de lo contrario se muestra la lista de cartas.
        if (error != null) {
            Text(
                text = error ?: "", modifier = Modifier.padding(16.dp)
            )
        } else {
            CardList(cards = cards)
        }
    }
}
