package com.javimutis.examplemvvm.data.network

import com.javimutis.examplemvvm.core.RetrofitHelper
import com.javimutis.examplemvvm.data.model.QuoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Clase que se encarga de comunicarse con la API mediante Retrofit.
class QuoteService @Inject constructor(private val api:QuoteApiClient) {

    // Función que obtiene las citas desde el servidor.
    suspend fun getQuotes(): List<QuoteModel> {
        return withContext(Dispatchers.IO) { // Ejecuta el código en un hilo secundario.
            val response = api.getAllQuotes() // Hace la petición.
            response.body() ?: emptyList() // Devuelve la lista de citas o una lista vacía si falla.
        }
    }
}
