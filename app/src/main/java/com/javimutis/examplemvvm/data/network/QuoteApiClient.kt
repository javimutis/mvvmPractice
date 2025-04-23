package com.javimutis.examplemvvm.data.network

import com.javimutis.examplemvvm.data.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

// Esta interfaz define cómo se comunica Retrofit con el servidor (API).
interface QuoteApiClient {

    // Esta función representa una solicitud HTTP GET a la ruta "/.json".
    // Devuelve una lista de citas envuelta en un objeto Response.
    @GET("/.json")
    suspend fun getAllQuotes(): Response<List<QuoteModel>>
}
