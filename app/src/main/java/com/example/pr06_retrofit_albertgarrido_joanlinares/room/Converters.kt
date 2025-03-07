package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import androidx.room.TypeConverter
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Tcgplayer
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Prices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromTcgplayer(tcgplayer: Tcgplayer?): String? {
        return gson.toJson(tcgplayer)
    }

    @TypeConverter
    fun toTcgplayer(json: String?): Tcgplayer? {
        return json?.let {
            gson.fromJson(it, object : TypeToken<Tcgplayer>() {}.type)
        }
    }

    @TypeConverter
    fun fromPrices(prices: Prices?): String? {
        return gson.toJson(prices)
    }

    @TypeConverter
    fun toPrices(json: String?): Prices? {
        return json?.let {
            gson.fromJson(it, object : TypeToken<Prices>() {}.type)
        }
    }
}
