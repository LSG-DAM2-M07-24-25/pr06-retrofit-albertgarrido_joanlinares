package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import android.app.Application
import androidx.room.Room.databaseBuilder

class PokemonApplication : Application() {
    // Atributo estático de la clase
    companion object {
        lateinit var database: PokemonDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // nombre base de datos
        database = databaseBuilder(this, PokemonDatabase::class.java,"PokemonDatabase").build()
    }
}