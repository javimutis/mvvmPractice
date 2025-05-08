package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

// Caso de uso para obtener una frase aleatoria de la base local.
class GetRandomQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {

    // Función que devuelve una frase aleatoria (o null si no hay ninguna guardada).
    suspend operator fun invoke(): Quote? {
        val quotes = repository.getAllQuotesFromDatabase() // Trae todas las frases locales.
        if (!quotes.isNullOrEmpty()) {                    // Si la lista no está vacía:
            val randomNumber = (quotes.indices).random()  // Elige un índice aleatorio.
            return quotes[randomNumber]                  // Devuelve la frase aleatoria.
        }
        return null                                       // Si no hay frases, devuelve null.
    }
}


