package com.example.pr06_retrofit_albertgarrido_joanlinares.api

import com.example.pr06_retrofit_albertgarrido_joanlinares.model.Card

/**
 * Repositorio encargado de manejar la obtención de cartas desde la API de Pokémon TCG.
 *
 * Actúa como una capa intermedia entre la API y el ViewModel, encapsulando la lógica
 * de red y asegurando un acceso estructurado a los datos.
 *
 * @property apiInterface Instancia de `APIInterface` utilizada para realizar las llamadas a la API.
 */
class CardRepository(private val apiInterface: APIInterface = APIInterface.create()) {

    /**
     * Obtiene todas las cartas Pokémon del set `sv8` mediante una llamada a la API.
     *
     * @return Una lista de objetos `Card` si la solicitud fue exitosa, o `null` si hubo un error.
     */
    suspend fun getAllCards(): List<Card>? {
        val response = apiInterface.getCards()
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }
}