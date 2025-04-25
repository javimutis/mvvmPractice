package com.javimutis.examplemvvm.data.network

import com.javimutis.examplemvvm.data.model.QuoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Clase que llama a la API usando Retrofit.
// Usa corrutinas para no bloquear la pantalla mientras descarga los datos.
class QuoteService @Inject constructor(private val api: QuoteApiClient) {

    // Función que llama a la API en segundo plano.
    suspend fun getQuotes(): List<QuoteModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllQuotes() // Llama a la API.
            response.body() ?: emptyList() // Si la respuesta existe, devuelve la lista; si no, lista vacía.
        }
    }
}

