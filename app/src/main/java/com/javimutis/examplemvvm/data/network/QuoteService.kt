package com.javimutis.examplemvvm.data.network

import com.javimutis.examplemvvm.data.model.QuoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Esta clase se encarga de llamar a la API usando Retrofit.
class QuoteService @Inject constructor(private val api: QuoteApiClient) {

    // Esta función hace la solicitud en un hilo de fondo para no congelar la app.
    suspend fun getQuotes(): List<QuoteModel> {
        return withContext(Dispatchers.IO) { // Hilo especializado para operaciones de red o disco.
            val response = api.getAllQuotes() // Llama a la API.
            response.body() ?: emptyList() // Si la respuesta es válida, devuelve la lista de citas. Si no, devuelve una lista vacía.
        }
    }
}
