package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.data.model.QuoteModel
import javax.inject.Inject

// Esta clase representa la lógica para obtener todas las citas.
class GetQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {

    // Esta función se puede invocar directamente para obtener la lista.
    suspend operator fun invoke(): List<QuoteModel>? = repository.getAllQuotes()
}
