package com.javimutis.examplemvvm.data

import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.data.model.QuoteProvider
import com.javimutis.examplemvvm.data.network.QuoteService
import javax.inject.Inject

// El repositorio es el intermediario entre la fuente de datos (API) y el resto de la app.
class QuoteRepository @Inject constructor(
    private val api: QuoteService, // Instancia del servicio que se comunica con la API.
    private val quoteProvider: QuoteProvider // Guarda las citas en el "provider".
) {


    // Función que obtiene todas las citas desde la API.
    suspend fun getAllQuotes(): List<QuoteModel> {
        val response = api.getQuotes() // Llama al servicio.
        quoteProvider.quotes = response
        return response // Devuelve la lista.
    }
}
