import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun CardDetails(
    navigationController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val card = homeViewModel.selectedCard.observeAsState().value
    // Estado para alternar el ícono de carrito
    var isCartFilled by remember { mutableStateOf(false) }

    if (card == null) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ítem para el ícono de carrito en la esquina superior derecha
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { isCartFilled = !isCartFilled },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .padding(bottom = 30.dp)
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = if (isCartFilled) Color.Black else Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            // Ítem para la imagen centrada y más grande
            item {
                Image(
                    painter = rememberAsyncImagePainter(model = card.images.large),
                    contentDescription = card.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )
            }
            // Espacio entre la imagen y el nombre
            item { Spacer(modifier = Modifier.height(16.dp)) }
            // Ítem para el nombre del Pokémon, centrado
            item {
                Text(
                    text = card.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            // Ítem para el tipo del Pokémon, debajo del nombre
            item {
                val cardType = card.types?.joinToString(", ") ?: card.supertype ?: "Desconocido"
                Text(
                    text = "Tipo: $cardType",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            // Espacio antes del botón de volver
            item { Spacer(modifier = Modifier.height(24.dp)) }
            // Ítem para el botón "Volver"
            item {
                Button(onClick = { navigationController.popBackStack() }) {
                    Text("Volver")
                }
            }
        }
    }
}
