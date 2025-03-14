package com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

/**
 * Composable genérico que representa una tarjeta (Card) para mostrar elementos con imagen y nombre.
 *
 * Se puede utilizar con cualquier tipo de objeto genérico `T`, siempre que se proporcionen funciones para obtener
 * la imagen y el nombre del elemento.
 *
 * @param item Elemento de tipo genérico `T` que se representará en la tarjeta.
 * @param onCardClick Acción a ejecutar cuando el usuario haga clic en la tarjeta.
 * @param imageProvider Función lambda que recibe el elemento `T` y devuelve la URL de su imagen.
 * @param nameProvider Función lambda que recibe el elemento `T` y devuelve su nombre como texto.
 */
@Composable
fun <T> CardItem(
    item: T,
    onCardClick: () -> Unit,
    imageProvider: (T) -> String,
    nameProvider: (T) -> String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = imageProvider(item)),
                contentDescription = nameProvider(item),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
        }
    }
}
