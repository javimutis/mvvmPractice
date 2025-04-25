package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

// Este UseCase se encarga de obtener una frase aleatoria desde la base de datos local.
class GetRandomQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {

    suspend operator fun invoke(): Quote? {
        val quotes = repository.getAllQuotesFromDatabase() // Pide todas las frases locales
        if (!quotes.isNullOrEmpty()) {
            val randomNumber = (quotes.indices).random() // Genera un índice aleatorio
            return quotes[randomNumber] // Devuelve una frase aleatoria
        }
        return null // Si no hay frases, devuelve null
    }
}

