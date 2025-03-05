package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.HomeViewModel

@Composable
fun CardDetails(
    navigationController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val selectedCard = homeViewModel.selectedCard.observeAsState().value
    var isCartFilled by remember { mutableStateOf(false) }

    // Consultar el estado del carrito para la carta seleccionada
    LaunchedEffect(selectedCard) {
        selectedCard?.let {
            isCartFilled = homeViewModel.isCardInCart(it.name)
        }
    }

    // Detectamos ancho disponible para breakpoints
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Si no hay carta seleccionada, mostramos error
        if (selectedCard == null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
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
            // Breakpoints para paddings
            val screenWidth = maxWidth
            val paddingValue = when {
                screenWidth < 600.dp -> 8.dp   // Pantalla pequeña
                screenWidth < 840.dp -> 16.dp  // Pantalla mediana
                else -> 24.dp                  // Pantalla grande
            }

            // También podemos definir minHeight y maxHeight según el ancho
            val (minHeight, maxHeight) = when {
                screenWidth < 600.dp -> 300.dp to 400.dp   // Pequeño
                screenWidth < 840.dp -> 400.dp to 600.dp   // Mediano
                else -> 500.dp to 700.dp                   // Grande
            }

            // Detectamos orientación
            val configuration = LocalConfiguration.current
            val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

            if (!isLandscape) {
                // ------------------ MODO VERTICAL ------------------
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Icono carrito en la parte superior
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp)) {
                            IconButton(
                                onClick = {
                                    isCartFilled = !isCartFilled
                                    homeViewModel.toggleCartStatus(selectedCard, isCartFilled)
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
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
                    // Imagen
                    item {
                        Image(
                            painter = rememberAsyncImagePainter(model = selectedCard.images.large),
                            contentDescription = selectedCard.name,
                            contentScale = ContentScale.Fit, // << Para que NO recorte
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = minHeight, max = maxHeight) // << Altura adaptativa
                        )
                    }
                    // Nombre de la carta
                    item {
                        Text(
                            text = selectedCard.name,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    // Tipo
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
                    // Espacio + Botón de volver
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { navigationController.popBackStack() }) {
                            Text("Volver")
                        }
                    }
                }
            } else {
                // ------------------ MODO HORIZONTAL ------------------
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Imagen a la izquierda
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedCard.images.large),
                        contentDescription = selectedCard.name,
                        contentScale = ContentScale.Fit, // << NO recorta
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = minHeight, max = maxHeight)
                            .padding(end = 8.dp)
                    )
                    // Columna con la info a la derecha
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = {
                                isCartFilled = !isCartFilled
                                homeViewModel.toggleCartStatus(selectedCard, isCartFilled)
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = if (isCartFilled) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                                contentDescription = "Carrito",
                                tint = if (isCartFilled) Color.Black else Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        Text(
                            text = selectedCard.name,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        val cardType = selectedCard.types?.joinToString(", ")
                            ?: selectedCard.supertype
                            ?: "Desconocido"
                        Text(
                            text = "Tipo: $cardType",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { navigationController.popBackStack() }) {
                            Text("Volver")
                        }
                    }
                }
            }
        }
    }
}
