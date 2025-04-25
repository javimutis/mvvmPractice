package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.data.database.entities.toDatabase
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

// Esta clase maneja la lógica para obtener frases.
// Si hay frases en la API, las guarda en la base local y devuelve.
// Si no hay en la API, las toma de la base de datos.
class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {
    suspend operator fun invoke(): List<Quote> {
        val quotes = repository.getAllQuotesFromApi()
        return if (quotes.isNotEmpty()) {
            repository.clearQuotes()
            repository.insertQuotes(quotes.map { it.toDatabase() })
            quotes
        } else {
            repository.getAllQuotesFromDatabase()
        }
    }
}
