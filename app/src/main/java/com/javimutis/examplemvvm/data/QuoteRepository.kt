package com.javimutis.examplemvvm.data

import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.data.model.QuoteProvider
import com.javimutis.examplemvvm.data.network.QuoteService

// El repositorio es el intermediario entre la fuente de datos (API) y el resto de la app.
class QuoteRepository {

    // Instancia del servicio que se comunica con la API.
    private val api = QuoteService()

    // Función que obtiene todas las citas desde la API.
    suspend fun getAllQuotes(): List<QuoteModel> {
        val response = api.getQuotes() // Llama al servicio.
        QuoteProvider.quotes = response // Guarda las citas en el "provider".
        return response // Devuelve la lista.
    }
}
