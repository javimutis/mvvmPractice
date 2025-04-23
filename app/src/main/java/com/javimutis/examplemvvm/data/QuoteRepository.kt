package com.javimutis.examplemvvm.data

import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.data.model.QuoteProvider
import com.javimutis.examplemvvm.data.network.QuoteService
import javax.inject.Inject

// El repositorio actúa como puente entre los datos de red y el resto de la app.
class QuoteRepository @Inject constructor(
    private val api: QuoteService,
    private val quoteProvider: QuoteProvider
) {
    // Esta función obtiene las citas desde internet y las guarda localmente en el Provider.
    suspend fun getAllQuotes(): List<QuoteModel> {
        val response = api.getQuotes()
        quoteProvider.quotes = response // Se almacenan para poder usarlas después (por ejemplo, para mostrar una aleatoria).
        return response
    }
}
