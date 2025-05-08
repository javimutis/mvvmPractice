package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.data.database.entities.toDatabase
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

// Caso de uso para obtener todas las frases, primero desde la API.
// Si la API responde con frases:
//    - Borra las frases locales.
//    - Inserta las nuevas.
// Si la API no responde, usa las frases locales.
class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {

    // suspend operator fun invoke() permite llamar esta clase como si fuera una función suspendida.
    suspend operator fun invoke(): List<Quote> {
        val quotes = repository.getAllQuotesFromApi()  // Pide las frases a la API.
        return if (quotes.isNotEmpty()) {             // Si llegaron frases nuevas:
            repository.insertQuotes(quotes.map { it.toDatabase() }) // Guarda esas frases en la base.
            quotes                                                 // Devuelve las frases nuevas.
        } else {
            repository.getAllQuotesFromDatabase() // Si no hay nuevas, carga las guardadas localmente.
        }
    }
}

