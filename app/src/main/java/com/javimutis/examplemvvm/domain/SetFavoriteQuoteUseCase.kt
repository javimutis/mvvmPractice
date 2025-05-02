package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

class SetFavoriteQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(quote: Quote, isFavorite: Boolean) {
        val updatedQuote = quote.copy(isFavorite = isFavorite)
        repository.updateQuoteFavoriteStatus(updatedQuote)
    }
}