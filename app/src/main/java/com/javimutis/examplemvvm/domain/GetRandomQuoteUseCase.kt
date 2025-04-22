package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.model.QuoteProvider
import com.javimutis.examplemvvm.data.model.QuoteModel

// Caso de uso: Obtener una cita aleatoria de la lista ya descargada.
class GetRandomQuoteUseCase {

    // Devuelve una cita aleatoria desde el QuoteProvider.
    operator fun invoke(): QuoteModel? {
        val quotes = QuoteProvider.quotes
        if (quotes.isNotEmpty()) {
            return quotes.random()
        }
        return null
    }
}
