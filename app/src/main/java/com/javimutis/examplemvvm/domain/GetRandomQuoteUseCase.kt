package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

// Este UseCase se encarga de obtener una frase aleatoria desde la base de datos local.
class GetRandomQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {

    // Esta función devuelve una frase aleatoria de las que están en la base de datos local.
    suspend operator fun invoke(): Quote? {
        val quotes = repository.getAllQuotesFromDatabase()
        if (!quotes.isNullOrEmpty()) {
            val randomNumber = (quotes.indices).random()
            return quotes[randomNumber]
        }
        return null
    }
}

