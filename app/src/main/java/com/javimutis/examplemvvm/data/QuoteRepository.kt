package com.javimutis.examplemvvm.data

import com.javimutis.examplemvvm.data.database.dao.QuoteDao
import com.javimutis.examplemvvm.data.database.entities.QuoteEntity
import com.javimutis.examplemvvm.data.database.entities.toDatabase
import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.data.network.QuoteService
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject
import com.javimutis.examplemvvm.domain.model.toDomain

// El repositorio actúa como puente entre la fuente de datos (API o base local) y la capa de dominio.
class QuoteRepository @Inject constructor(
    private val api: QuoteService,    // Fuente remota (API).
    private val quoteDao: QuoteDao    // Fuente local (base de datos).
) {

    // Obtiene las frases desde la API remota (servidor).
    suspend fun getAllQuotesFromApi(): List<Quote> {
        val response: List<QuoteModel> = api.getQuotes()     // Llama al servicio de red.
        return response.map { it.toDomain() }               // Convierte los datos al modelo del dominio.
    }

    // Obtiene las frases guardadas en la base de datos local.
    suspend fun getAllQuotesFromDatabase(): List<Quote> {
        val response: List<QuoteEntity> = quoteDao.getAllQuotes()  // Consulta la base local.
        return response.map { it.toDomain() }                      // Convierte al modelo del dominio.
    }

    // Inserta nuevas frases en la base de datos, evitando duplicados.
    suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        for (quote in quotes) {
            val existing = quoteDao.getQuoteByText(quote.quote)   // Verifica si ya existe.
            if (existing == null) {
                quoteDao.insert(quote)                            // Si no existe, la inserta.
            }
        }
    }

    // Borra todas las frases de la base de datos.
    suspend fun clearQuotes() {
        quoteDao.deleteAllQuotes()
    }

    // Actualiza el estado de favorito de una frase específica.
    suspend fun updateQuoteFavoriteStatus(quote: Quote) {
        val entity = QuoteEntity(
            quote = quote.quote,
            author = quote.author,
            isFavorite = quote.isFavorite
        )
        quoteDao.updateQuote(entity)  // Llama al DAO para actualizar en la base.
    }
}
