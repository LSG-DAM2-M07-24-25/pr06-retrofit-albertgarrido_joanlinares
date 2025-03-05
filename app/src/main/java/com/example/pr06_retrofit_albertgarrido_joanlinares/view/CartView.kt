package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.card.CardList
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.CartViewModel

@Composable
fun CartView(
    navigationController: NavHostController,
    cartViewModel: CartViewModel = viewModel()
) {
    // Se obtienen los Pokémon favoritos desde la BD filtrados en el ViewModel
    val favouriteItems by cartViewModel.cartItems.observeAsState(emptyList())

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = maxWidth
        val paddingValue = when {
            screenWidth < 600.dp -> 8.dp   // Pantalla pequeña
            screenWidth < 840.dp -> 16.dp  // Pantalla mediana
            else -> 24.dp                  // Pantalla grande
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(top = 50.dp),
            contentPadding = PaddingValues(paddingValue)
        ) {
            // Botón para volver a la pantalla anterior
            item {
                Button(
                    modifier = Modifier.padding(bottom = paddingValue),
                    onClick = { navigationController.popBackStack() }
                ) {
                    Text(text = "Volver a Cartas")
                }
            }
            // Reutilizamos CardList para mostrar las cartas favoritas (usando Pokémon directamente)
            item {
                CardList(
                    items = favouriteItems,
                    onCardClick = { pokemon ->
                        navigationController.navigate(Routes.Screen2.route)
                    },
                    imageProvider = { pokemon -> pokemon.images.small },
                    nameProvider = { pokemon -> pokemon.name }
                )

            }
        }
    }
}
