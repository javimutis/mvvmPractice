package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

// Esta clase devuelve una frase aleatoria de las que están guardadas localmente.
class GetRandomQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {

    suspend operator fun invoke(): Quote? {
        val quotes = repository.getAllQuotesFromDatabase()
        if (!quotes.isNullOrEmpty()) {
            val randomNumber = (quotes.indices).random()
            return quotes[randomNumber]
        }
        return null // Si no hay frases, devuelve null.
    }
}

