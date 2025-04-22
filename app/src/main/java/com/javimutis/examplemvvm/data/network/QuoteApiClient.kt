package com.javimutis.examplemvvm.data.network

import com.javimutis.examplemvvm.data.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

// Esta interfaz define cómo Retrofit debe comunicarse con el servidor.
// Aquí indicamos las peticiones HTTP que se pueden hacer.
interface QuoteApiClient {

    // Esta función representa una petición GET a la URL base + "/.json"
    // Devuelve una lista de citas dentro de un Response.
    @GET("/.json")
    suspend fun getAllQuotes(): Response<List<QuoteModel>>
}
