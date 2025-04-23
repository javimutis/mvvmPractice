package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.data.model.QuoteModel
import javax.inject.Inject

// Esta clase representa un caso de uso: "Obtener todas las citas".
// Lógica de negocio desacoplada del resto de la app.
class GetQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {


    // Función que se puede invocar directamente para obtener las citas.
    suspend operator fun invoke(): List<QuoteModel>? = repository.getAllQuotes()
}
