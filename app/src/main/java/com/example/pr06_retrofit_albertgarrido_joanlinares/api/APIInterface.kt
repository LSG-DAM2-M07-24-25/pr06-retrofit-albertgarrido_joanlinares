package com.example.pr06_retrofit_albertgarrido_joanlinares.api

import android.provider.ContactsContract
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIInterface {

    @GET("characters")
    suspend fun getCharacters(): Response<ContactsContract.Contacts.Data>

    companion object {
        private const val BASE_URL = ""

        fun create(): APIInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(APIInterface::class.java)
        }
    }
}