package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun CardDetails(
    navigationController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val selectedCard = homeViewModel.selectedCard.observeAsState().value
    // Estado local para el icono del carrito
    var isCartFilled by remember { mutableStateOf(false) }

    // Usamos un coroutineScope local para lanzar corrutinas desde este composable
    val composableScope = rememberCoroutineScope()

    // Cuando `selectedCard` cambie, consultamos si está en el carrito
    LaunchedEffect(selectedCard) {
        if (selectedCard != null) {
            // Llamamos a isCardInCart en un hilo de corrutina
            val inCart = homeViewModel.isCardInCart(selectedCard.name)
            isCartFilled = inCart
        }
    }

    if (selectedCard == null) {
        // Si no hay carta seleccionada, mostramos un mensaje
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Text(
                    text = "Carta no encontrada",
                    fontSize = 22.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navigationController.popBackStack() }) {
                    Text(text = "Volver")
                }
            }
        }
    } else {
        // Si la carta existe, la mostramos
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Icono en la esquina superior derecha
                    IconButton(
                        onClick = {
                            // Cambiamos el estado local
                            isCartFilled = !isCartFilled
                            // Llamamos a la función del ViewModel para añadir o quitar de la BD
                            homeViewModel.toggleCartStatus(selectedCard, isCartFilled)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (isCartFilled) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = if (isCartFilled) Color.Black else Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            item {
                Image(
                    painter = rememberAsyncImagePainter(model = selectedCard.images.large),
                    contentDescription = selectedCard.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Text(
                    text = selectedCard.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            item {
                val cardType = selectedCard.types?.joinToString(", ")
                    ?: selectedCard.supertype
                    ?: "Desconocido"
                Text(
                    text = "Tipo: $cardType",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                Button(onClick = { navigationController.popBackStack() }) {
                    Text("Volver")
                }
            }
        }
    }
}
