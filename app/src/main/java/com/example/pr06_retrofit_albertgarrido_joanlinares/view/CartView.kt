package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.CartViewModel

@Composable
fun CartView(
    navigationController: NavHostController,
    cartViewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // Obtenemos la lista de Pokémon en el LiveData
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Un botón para volver a la lista de cartas
        item {
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    navigationController.popBackStack()
                }
            ) {
                Text(text = "Volver a Cartas")
            }
        }
        // Mostramos cada Pokémon del carrito
        items(cartItems) { pokemon ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                // Usamos Coil (rememberAsyncImagePainter) para URL
                Image(
                    painter = rememberAsyncImagePainter(model = pokemon.image),
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 16.dp)
                )
                Column {
                    Text(
                        text = pokemon.name,
                        fontSize = 18.sp
                    )
                    Text(
                        text = pokemon.type,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

