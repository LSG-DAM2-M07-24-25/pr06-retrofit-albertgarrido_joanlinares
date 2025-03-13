package com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

/**
 * Composable que muestra una lista de tarjetas (`CardItem`) con un diseño adaptable
 * según la orientación del dispositivo.
 *
 * - En **modo vertical**, las tarjetas se muestran en una columna.
 * - En **modo horizontal**, las tarjetas se organizan en filas de hasta 3 elementos.
 *
 * @param items Lista de elementos de tipo `T` a mostrar en las tarjetas.
 * @param onCardClick Acción a ejecutar cuando el usuario haga clic en una tarjeta.
 * @param imageProvider Función lambda que recibe un elemento `T` y devuelve la URL de su imagen.
 * @param nameProvider Función lambda que recibe un elemento `T` y devuelve su nombre como texto.
 */
@Composable
fun <T> CardList(
    items: List<T>,
    onCardClick: (T) -> Unit,
    imageProvider: (T) -> String,
    nameProvider: (T) -> String
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        val rows = items.chunked(3)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            rows.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            CardItem(
                                item = item,
                                onCardClick = { onCardClick(item) },
                                imageProvider = imageProvider,
                                nameProvider = nameProvider
                            )
                        }
                    }
                    if (rowItems.size < 3) {
                        repeat(3 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                CardItem(
                    item = item,
                    onCardClick = { onCardClick(item) },
                    imageProvider = imageProvider,
                    nameProvider = nameProvider
                )
            }
        }
    }
}
