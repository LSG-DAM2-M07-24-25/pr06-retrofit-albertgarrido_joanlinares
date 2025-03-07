package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon

@Database(entities = [Pokemon::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}