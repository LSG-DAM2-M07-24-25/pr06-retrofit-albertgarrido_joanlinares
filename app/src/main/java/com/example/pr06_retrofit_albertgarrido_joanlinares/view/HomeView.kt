package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.card.CardList
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.HomeViewModel

@Composable
fun HomeView(navigationController: NavHostController, homeViewModel: HomeViewModel) {
    val cards by homeViewModel.cards.observeAsState(emptyList())
    val error by homeViewModel.error.observeAsState()

    // BoxWithConstraints para adaptar el padding según el tamaño de pantalla
    BoxWithConstraints {
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
        ) {
            item {
                Button(
                    modifier = Modifier
                        .padding(paddingValue)
                        .padding(top = 50.dp),
                    onClick = {
                        navigationController.navigate(Routes.Screen3.route)
                    }
                ) {
                    Text(text = "Carrito")
                }
            }
            item {
                if (error != null) {
                    Text(
                        text = error ?: "",
                        modifier = Modifier.padding(paddingValue)
                    )
                } else {
                    CardList(
                        items = cards,
                        imageProvider = { card -> card.images.small },
                        nameProvider = { card -> card.name },
                        onCardClick = { card ->
                            // Actualizar LiveData con la carta seleccionada y navegar a detalles
                            homeViewModel.selectCard(card)
                            navigationController.navigate(Routes.Screen2.route)
                        }
                    )
                }
            }
        }
    }
}
