package com.javimutis.examplemvvm.data.network

import com.javimutis.examplemvvm.core.RetrofitHelper
import com.javimutis.examplemvvm.data.model.QuoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Clase que se encarga de comunicarse con la API mediante Retrofit.
class QuoteService {

    // Se obtiene una instancia de Retrofit ya configurada.
    private val retrofit = RetrofitHelper.getRetrofit()

    // Función que obtiene las citas desde el servidor.
    suspend fun getQuotes(): List<QuoteModel> {
        return withContext(Dispatchers.IO) { // Ejecuta el código en un hilo secundario.
            val response = retrofit.create(QuoteApiClient::class.java).getAllQuotes() // Hace la petición.
            response.body() ?: emptyList() // Devuelve la lista de citas o una lista vacía si falla.
        }
    }
}
