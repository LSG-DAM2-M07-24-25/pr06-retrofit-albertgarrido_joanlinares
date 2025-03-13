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
import com.example.pr06_retrofit_albertgarrido_joanlinares.viewmodel.SearchBarViewModel

/**
 * Composable que define el punto de entrada y gestiona la navegación principal de la aplicación Pokémon.
 *
 * Utiliza NavHost para gestionar las rutas principales, incluyendo la pantalla principal de cartas,
 * la vista de detalles individuales de cada carta, el carrito de cartas favoritas y la pantalla de búsqueda avanzada.
 *
 * @param navigationController Controlador principal de navegación para gestionar las rutas.
 * @param homeViewModel ViewModel que administra el estado de la vista principal y detalles de cartas.
 * @param cartViewModel ViewModel que gestiona el estado del carrito local.
 * @param searchBarViewModel ViewModel que administra la lógica y el historial de la barra de búsqueda.
 */
@Composable
fun EntryPoint(navigationController: NavController, homeViewModel: HomeViewModel = viewModel(), cartViewModel: CartViewModel = viewModel(), searchBarViewModel: SearchBarViewModel = viewModel()) {

    NavHost(
        navController = navigationController as NavHostController,
        startDestination = Routes.Screen1.route
    ) {
        composable(Routes.Screen1.route) {
            HomeView(navigationController, homeViewModel, searchBarViewModel)
        }
        composable(Routes.Screen2.route) {
            CardDetails(navigationController, homeViewModel)
        }

        composable(Routes.Screen3.route) {
            CartView(navigationController, cartViewModel, homeViewModel)
        }

        composable(Routes.Screen4.route) {
            SearchView(navigationController, searchBarViewModel)
        }

    }
}