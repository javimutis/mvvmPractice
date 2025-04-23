package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.model.QuoteProvider
import com.javimutis.examplemvvm.data.model.QuoteModel
import javax.inject.Inject

// Esta clase obtiene una cita aleatoria ya descargada.
class GetRandomQuoteUseCase @Inject constructor(private val quoteProvider: QuoteProvider) {

    operator fun invoke(): QuoteModel? {
        val quotes = quoteProvider.quotes
        if (!quotes.isNullOrEmpty()) {
            val randomNumber = (quotes.indices).random() // Elige un índice aleatorio.
            return quotes[randomNumber] // Devuelve la cita seleccionada.
        }
        return null // Si no hay citas, devuelve null.
    }
}
