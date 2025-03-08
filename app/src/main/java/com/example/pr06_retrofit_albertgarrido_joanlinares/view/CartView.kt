package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util.CardList
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.CartViewModel
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.HomeViewModel

@Composable
fun CartView(
    navigationController: NavHostController,
    cartViewModel: CartViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
) {
    val favouriteItems by cartViewModel.cartItems.observeAsState(emptyList())
    val totalPrice by cartViewModel.totalPrice.observeAsState(0f)

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
                .padding(top = 20.dp),
            contentPadding = PaddingValues(paddingValue)
        ) {
            item {
                Button(
                    modifier = Modifier.padding(bottom = paddingValue),
                    onClick = { navigationController.popBackStack() }
                ) {
                    Text(text = "Volver a Cartas")
                }
            }
            item {
                CardList(
                    items = favouriteItems,
                    onCardClick = { pokemon ->
                        homeViewModel.selectCard(pokemon)
                        navigationController.navigate(Routes.Screen2.route)
                    },
                    imageProvider = { pokemon -> pokemon.images.small },
                    nameProvider = { pokemon -> pokemon.name }
                )
            }
            // Agregamos un item para mostrar el total
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Total: ${"%.2f".format(totalPrice)}€",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
