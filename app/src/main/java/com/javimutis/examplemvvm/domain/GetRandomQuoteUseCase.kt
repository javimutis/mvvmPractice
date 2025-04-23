package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.model.QuoteProvider
import com.javimutis.examplemvvm.data.model.QuoteModel
import javax.inject.Inject

// Caso de uso: Obtener una cita aleatoria de la lista ya descargada.
class GetRandomQuoteUseCase @Inject constructor(private val quoteProvider: QuoteProvider) {

    // Devuelve una cita aleatoria desde el QuoteProvider.
    operator fun invoke(): QuoteModel? {
        val quotes = quoteProvider.quotes
        if (!quotes.isNullOrEmpty()) {
            val randomNumber = (quotes.indices).random()
            return quotes[randomNumber]
        }
        return null
    }
}
