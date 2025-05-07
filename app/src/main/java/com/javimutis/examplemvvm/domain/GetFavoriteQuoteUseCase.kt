package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    operator fun invoke(): Flow<List<Quote>> {
        return repository.getFavoriteQuotes()
    }
}
