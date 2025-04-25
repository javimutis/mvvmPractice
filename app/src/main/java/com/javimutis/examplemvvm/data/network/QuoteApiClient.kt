package com.javimutis.examplemvvm.data.network

import com.javimutis.examplemvvm.data.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

// Interfaz que describe cómo Retrofit se comunica con la API.
interface QuoteApiClient {

    // Método para obtener todas las frases desde la API (GET request a la raíz).
    @GET("/.json")
    suspend fun getAllQuotes(): Response<List<QuoteModel>>
}

