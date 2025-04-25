package com.javimutis.examplemvvm.data

import com.javimutis.examplemvvm.data.database.dao.QuoteDao
import com.javimutis.examplemvvm.data.database.entities.QuoteEntity
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

    // Trae todas las frases desde la API.
    suspend fun getAllQuotesFromApi(): List<Quote> {
        val response: List<QuoteModel> = api.getQuotes()
        return response.map { it.toDomain() } // Convierte los datos a objetos del dominio.
    }

    // Trae todas las frases guardadas en la base de datos local.
    suspend fun getAllQuotesFromDatabase(): List<Quote> {
        val response: List<QuoteEntity> = quoteDao.getAllQuotes()
        return response.map { it.toDomain() }
    }

    // Guarda una lista de frases en la base de datos.
    suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        quoteDao.insertAll(quotes)
    }

    // Borra todas las frases guardadas.
    suspend fun clearQuotes() {
        quoteDao.deleteAllQuotes()
    }
}
