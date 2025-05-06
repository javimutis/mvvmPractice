package com.javimutis.examplemvvm.domain

import androidx.lifecycle.LiveData
import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

import kotlinx.coroutines.flow.Flow

class GetFavoriteQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    operator fun invoke(): Flow<List<Quote>> {
        return repository.getFavoriteQuotes()
    }
}
