package com.example.pr06_retrofit_albertgarrido_joanlinares.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pr06_retrofit_albertgarrido_joanlinares.nav.Routes


@Composable
fun EntryPoint(navigationController: NavController) {

    NavHost(
        navController = navigationController as NavHostController,
        startDestination = Routes.Screen1.route
    ) {
        composable(Routes.Screen1.route) {
            HomeView(navigationController)
        }
        composable(Routes.Screen2.route) {
            CardDetails(navigationController)
        }

        composable(Routes.Screen3.route) {
            AlbumView(navigationController)
        }
    }
}