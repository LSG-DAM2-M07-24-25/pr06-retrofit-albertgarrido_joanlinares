package com.example.pr06_retrofit_albertgarrido_joanlinares

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.pr06_retrofit_albertgarrido_joanlinares.ui.theme.Pr06retrofitalbertgarrido_joanlinaresTheme
import com.example.pr06_retrofit_albertgarrido_joanlinares.view.EntryPoint

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pr06retrofitalbertgarrido_joanlinaresTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navigationController = rememberNavController()
                    //val windowSizeClass = calculateWindowSizeClass(this)
                    EntryPoint(navigationController)
                }
            }
        }
    }
}