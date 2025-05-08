package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Caso de uso (UseCase) para obtener las frases favoritas del usuario.
class GetFavoriteQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository  // Usa el repositorio para acceder a los datos.
) {
    // operator fun invoke() permite llamar este caso de uso como si fuera una función.
    operator fun invoke(): Flow<List<Quote>> {
        return repository.getFavoriteQuotes() // Pide al repositorio las frases favoritas.
    }
}

