package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util.SearchBar
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.SearchBarViewModel

@Composable
fun SearchView(
    navigationController: NavHostController,
    searchBarViewModel: SearchBarViewModel = viewModel()
) {
    val searchHistory by searchBarViewModel.searchHistory.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // SearchBar con historial
        SearchBar(
            searchBarViewModel = searchBarViewModel,
            navigationController = navigationController,
            onSearchComplete = { navigationController.popBackStack() },
            active = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(searchHistory) { text ->
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            searchBarViewModel.selectSearchTerm(text)
                            searchBarViewModel.onSearch(text)
                            navigationController.popBackStack()
                        }
                )
            }
        }
    }
}
