package com.example.pr06_retrofit_albertgarrido_joanlinares.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class Pokemon(
    @PrimaryKey val name: String,
    val type: String,
    val image: String,
    @ColumnInfo(name = "is_favorite") var addedToCart: Boolean = false
)