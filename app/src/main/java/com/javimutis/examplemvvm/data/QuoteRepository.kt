package com.javimutis.examplemvvm.data

import com.javimutis.examplemvvm.data.database.dao.QuoteDao
import com.javimutis.examplemvvm.data.database.entities.QuoteEntity
import com.javimutis.examplemvvm.data.database.entities.toDatabase
import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.data.network.QuoteService
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject
import com.javimutis.examplemvvm.domain.model.toDomain

// El repositorio organiza de dónde vienen los datos: API o base de datos local.
class QuoteRepository @Inject constructor(
    private val api: QuoteService,
    private val quoteDao: QuoteDao
) {

    // Obtiene las frases desde la API.
    suspend fun getAllQuotesFromApi(): List<Quote> {
        val response: List<QuoteModel> = api.getQuotes()
        return response.map { it.toDomain() }
    }

    // Obtiene las frases guardadas localmente.
    suspend fun getAllQuotesFromDatabase(): List<Quote> {
        val response: List<QuoteEntity> = quoteDao.getAllQuotes()
        return response.map { it.toDomain() }
    }

    // Inserta frases nuevas en la base de datos (evita duplicados).
    suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        for (quote in quotes) {
            val existing = quoteDao.getQuoteByText(quote.quote)
            if (existing == null) {
                quoteDao.insert(quote)
            }
        }
    }

    // Borra todas las frases.
    suspend fun clearQuotes() {
        quoteDao.deleteAllQuotes()
    }
    suspend fun updateQuoteFavoriteStatus(quote: Quote){
        val entity = QuoteEntity(
            quote = quote.quote,
            author = quote.author,
            isFavorite = quote.isFavorite
        )
        quoteDao.updateQuote(entity)
    }
}
