package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card as PokemonCard
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.HomeViewModel
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.getValue

@Composable
fun CardDetails(navController: NavController, cardId: String?) {
    // Obtenemos la lista de cartas desde HomeViewModel
    val homeViewModel: HomeViewModel = viewModel()
    val cards by homeViewModel.cards.collectAsState()
    val card = cards.find { it.id == cardId }

    // Si no se encuentra la carta, mostramos un mensaje de error
    if (card == null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            item {
                Text(
                    text = "Carta no encontrada",
                    fontSize = 22.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text(text = "Volver")
                }
            }
        }
        return
    }

    // (Opcional) Ejecutar alguna acción al cargar la carta
    LaunchedEffect(card) {
        // Por ejemplo, podrías cargar detalles adicionales
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen y icono de "colección" (fijo, sin lógica de favorito)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = card.images.large),
                    contentDescription = card.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 0.dp, // Sin borde, ya que no se aplica favorito
                            color = Color.Transparent,
                            shape = CircleShape
                        )
                )
                IconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = { /* Sin acción por ahora */ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Colección vacía",
                        tint = Color.Red
                    )
                }
            }
        }
        // Nombre de la carta
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = card.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        // Tipo de la carta
        item {
            Spacer(modifier = Modifier.height(8.dp))
            val cardType = card.types?.joinToString(", ") ?: card.supertype ?: "Desconocido"
            Text(
                text = "Tipo: $cardType",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
        // Espaciado extra
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        // Botón para volver atrás
        item {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text("Volver")
            }
        }
    }
}
