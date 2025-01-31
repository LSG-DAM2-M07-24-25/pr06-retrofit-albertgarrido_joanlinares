package com.example.pr06_retrofit_albertgarrido_joanlinares.nav

sealed class Routes(val route: String) {
    object Screen1 : Routes("screen1")

    object Screen2 : Routes("screen2") {
    }

}