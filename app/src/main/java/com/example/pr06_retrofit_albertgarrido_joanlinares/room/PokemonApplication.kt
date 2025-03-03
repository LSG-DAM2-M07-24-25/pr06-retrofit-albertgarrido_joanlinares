package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import android.app.Application
import androidx.room.Room
import androidx.room.Room.databaseBuilder

class PokemonApplication : Application() {
    // Creem un atribut estàtic de la classe
    companion object {
        lateinit var database: PokemonDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // Aquí determinem el nom de la base de dades. En el nostre cas "PokemonDatabase"
        database = databaseBuilder(this, PokemonDatabase::class.java,"PokemonDatabase").build()
    }
}