package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.HomeViewModel
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.CartViewModel


@Composable
fun EntryPoint(navigationController: NavController, homeViewModel: HomeViewModel = viewModel(), cartViewModel: CartViewModel = viewModel()) {

    NavHost(
        navController = navigationController as NavHostController,
        startDestination = Routes.Screen1.route
    ) {
        composable(Routes.Screen1.route) {
            HomeView(navigationController, homeViewModel)
        }
        composable(Routes.Screen2.route) {
            CardDetails(navigationController, homeViewModel)
        }

        composable(Routes.Screen3.route) {
            CartView(navigationController, cartViewModel, homeViewModel)
        }
    }
}