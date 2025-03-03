package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import androidx.room.RoomDatabase

abstract class PokemonDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}