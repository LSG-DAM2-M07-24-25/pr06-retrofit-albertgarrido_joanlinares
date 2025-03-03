package com.example.pr06_retrofit_albertgarrido_joanlinares.ui.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card as PokemonCard

@Composable
fun CardList(
    cards: List<PokemonCard>,
    onCardClick: (PokemonCard) -> Unit  // Callback que recibe la carta clickeada
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        cards.forEach { card ->
            CardItem(card = card, onCardClick = { onCardClick(card) })
        }
    }
}

@Composable
fun CardItem(card: PokemonCard, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = card.images.small),
                contentDescription = card.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),  // Puedes ajustar o quitar la altura fija si lo deseas
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

