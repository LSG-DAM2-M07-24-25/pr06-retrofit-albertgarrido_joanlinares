package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Pokemon
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.SearchHistoryEntry

@Database(entities = [Pokemon::class, SearchHistoryEntry::class], version = 2, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}
