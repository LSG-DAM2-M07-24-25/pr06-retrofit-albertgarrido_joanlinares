package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon

@Database(entities = [Pokemon::class], version = 2, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}