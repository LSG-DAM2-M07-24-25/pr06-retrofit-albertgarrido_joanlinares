package com.example.pr06_retrofit_albertgarrido_joanlinares.api

import androidx.tracing.perfetto.handshake.protocol.Response
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.CardResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIInterface {

    @GET("cards?q=set.id:sv8")
    suspend fun getCards(): retrofit2.Response<CardResponse>

    companion object {
        private const val BASE_URL = "https://api.pokemontcg.io/v2/"

        fun create(): APIInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            return retrofit.create(APIInterface::class.java)
        }
    }
}