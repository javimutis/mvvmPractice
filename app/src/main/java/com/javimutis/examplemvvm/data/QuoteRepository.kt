package com.javimutis.examplemvvm.data

import com.javimutis.examplemvvm.data.database.dao.QuoteDao
import com.javimutis.examplemvvm.data.database.entities.QuoteEntity
import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.data.network.QuoteService
import com.javimutis.examplemvvm.domain.model.Quote
import com.javimutis.examplemvvm.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


// El repositorio actúa como puente entre la fuente de datos (API o base local) y la capa de dominio.
class QuoteRepository @Inject constructor(
    private val api: QuoteService,    // Servicio para obtener frases remotas.
    private val quoteDao: QuoteDao    // DAO para acceder a la base local.
) {

    // Obtiene las frases desde la API y las convierte al modelo de dominio.
    suspend fun getAllQuotesFromApi(): List<Quote> {
        val response: List<QuoteModel> = api.getQuotes()
        return response.map { it.toDomain() }
    }

    // Obtiene las frases desde la base de datos local y las convierte al modelo de dominio.
    suspend fun getAllQuotesFromDatabase(): List<Quote> {
        val response: List<QuoteEntity> = quoteDao.getAllQuotes()
        return response.map { it.toDomain() }
    }

    // Inserta una lista de frases en la base de datos.
    suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        for (quote in quotes) {
            val existing = quoteDao.getQuoteByText(quote.quote) // Revisa si ya existe.
            if (existing == null) {
                quoteDao.insert(quote) // Si no existe, la inserta.
            } else {
                // Si existe, actualiza el autor pero mantiene el estado favorito.
                quoteDao.updateQuote(existing.copy(author = quote.author))
            }
        }
    }

    // Borra todas las frases guardadas.
    suspend fun clearQuotes() {
        quoteDao.deleteAllQuotes()
    }

    // Actualiza el estado de favorito de una frase.
    suspend fun updateQuoteFavoriteStatus(quote: Quote) {
        val entity = QuoteEntity(
            quote = quote.quote,
            author = quote.author,
            isFavorite = quote.isFavorite
        )
        quoteDao.updateQuote(entity)
    }

    // Obtiene las frases favoritas como Flow para observar cambios en tiempo real.
    fun getFavoriteQuotes(): Flow<List<Quote>> {
        return quoteDao.getFavoriteQuotes()
            .map { entities -> entities.map { it.toDomain() } } // Convierte a modelo de dominio.
    }
}
