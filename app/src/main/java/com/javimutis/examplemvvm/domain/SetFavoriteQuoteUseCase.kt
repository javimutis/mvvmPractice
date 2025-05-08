package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

// Caso de uso para marcar o desmarcar una frase como favorita.
class SetFavoriteQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository  // Accede al repositorio para actualizar datos.
) {
    // suspend operator fun invoke permite llamar la clase como función suspendida.
    suspend operator fun invoke(quote: Quote, isFavorite: Boolean) {
        // Crea una copia de la frase con el nuevo estado favorito.
        val updatedQuote = quote.copy(isFavorite = isFavorite)

        // Le pide al repositorio que actualice la frase en la base de datos.
        repository.updateQuoteFavoriteStatus(updatedQuote)
    }
}

