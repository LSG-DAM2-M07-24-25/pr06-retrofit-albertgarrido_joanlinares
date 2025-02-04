package com.example.pr06_retrofit_albertgarrido_joanlinares.api

import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card

class CardRepository(private val apiInterface: APIInterface = APIInterface.create()) {

    // Llama a la API y devuelve la lista de cartas o null en caso de error.
    suspend fun getAllCards(): List<Card>? {
        val response = apiInterface.getCards()
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }
}