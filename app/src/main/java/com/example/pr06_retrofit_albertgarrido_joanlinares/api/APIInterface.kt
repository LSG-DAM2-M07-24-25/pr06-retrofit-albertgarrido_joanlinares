package com.example.pr06_retrofit_albertgarrido_joanlinares.api

import androidx.tracing.perfetto.handshake.protocol.Response
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.CardResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Interfaz de API que define los métodos para interactuar con la API de Pokémon TCG.
 * Utiliza Retrofit para realizar llamadas HTTP y obtener los datos de las cartas Pokémon.
 */
interface APIInterface {

    /**
     * Obtiene una lista de cartas Pokémon del set con ID `sv8`.
     *
     * @return Una respuesta de Retrofit que contiene un objeto `CardResponse`
     * con la lista de cartas obtenidas de la API.
     */
    @GET("cards?q=set.id:sv8")
    suspend fun getCards(): retrofit2.Response<CardResponse>

    /**
     * Compañero de objeto que proporciona un método para crear una instancia de `APIInterface`.
     */
    companion object {
        private const val BASE_URL = "https://api.pokemontcg.io/v2/"

        /**
         * Crea e inicializa una instancia de `APIInterface` con Retrofit.
         *
         * @return Una implementación de `APIInterface` lista para realizar llamadas a la API.
         */
        fun create(): APIInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            return retrofit.create(APIInterface::class.java)
        }
    }
}