package com.example.pr06_retrofit_albertgarrido_joanlinares.ui.card

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageProvider(item)),
                        contentDescription = nameProvider(item),
                        modifier = Modifier
                            .width(300.dp)
                            .height(400.dp)
                            .align(Alignment.Center),
                        contentScale = ContentScale.FillBounds
                    )
                }
            } else {
                Image(
                    painter = rememberAsyncImagePainter(model = imageProvider(item)),
                    contentDescription = nameProvider(item),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
