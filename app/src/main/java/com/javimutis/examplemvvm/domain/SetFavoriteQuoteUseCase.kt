package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

// Caso de uso: cambia el estado de favorito de una frase.
class SetFavoriteQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository  // Recibe el repositorio para acceder a los datos.
) {
    // Operador invoke: permite llamar esta clase como si fuera una función.
    suspend operator fun invoke(quote: Quote, isFavorite: Boolean) {
        // Crea una copia de la cita, pero cambiando el estado de favorito.
        val updatedQuote = quote.copy(isFavorite = isFavorite)

        // Llama al repositorio para actualizar la cita en la base de datos.
        repository.updateQuoteFavoriteStatus(updatedQuote)
    }
}
