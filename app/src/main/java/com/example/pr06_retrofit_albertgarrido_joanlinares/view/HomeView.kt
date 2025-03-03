package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.card.CardList
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.HomeViewModel


@Composable
fun HomeView(navigationController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel()
    val cards by homeViewModel.cards.collectAsState()
    val error by homeViewModel.error.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Button(modifier = Modifier
                .padding(16.dp),
                onClick = {
                navigationController.navigate(Routes.Screen3.route)
            }) {
                Text(text = "Álbum")
            }
        }

        item {
            if (error != null) {
                Text(
                    text = error ?: "", modifier = Modifier.padding(16.dp)
                )
            } else {
                CardList(cards = cards, onCardClick = {
                    navigationController.navigate("${Routes.Screen2.route}/${it.id}")
                })
            }
        }
    }
}

