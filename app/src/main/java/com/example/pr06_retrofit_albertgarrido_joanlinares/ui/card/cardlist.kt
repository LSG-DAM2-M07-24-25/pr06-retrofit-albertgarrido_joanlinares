package com.example.pr06_retrofit_albertgarrido_joanlinares.ui.card

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun <T> CardList(
    items: List<T>,
    onCardClick: (T) -> Unit,
    imageProvider: (T) -> String,
    nameProvider: (T) -> String
) {
    Column(modifier = Modifier.fillMaxSize()) {
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

@Composable
fun <T> CardItem(
    item: T,
    onCardClick: () -> Unit,
    imageProvider: (T) -> String,
    nameProvider: (T) -> String
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            if (isLandscape) {
                // Modo horizontal: la imagen más pequeña y centrada, pero con aspecto automático
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageProvider(item)),
                        contentDescription = nameProvider(item),
                        modifier = Modifier
                            // Un ancho fijo opcional, o un fillMaxWidth(0.5f), etc.
                            .width(300.dp)
                            // Mantén una relación de aspecto, p.ej. 16:9 ≈ 1.77f
                            .aspectRatio(1.4f),
                        contentScale = ContentScale.Fit
                    )
                }
            } else {
                // Modo vertical: ocupa todo el ancho, altura calculada por aspectRatio
                Image(
                    painter = rememberAsyncImagePainter(model = imageProvider(item)),
                    contentDescription = nameProvider(item),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.4f), // Ajusta la relación que quieras
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
