package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.data.database.entities.toDatabase
import com.javimutis.examplemvvm.domain.model.Quote
import javax.inject.Inject

// Este UseCase maneja la lógica para obtener las frases (quotes).
// Intenta traer las frases desde la API. Si encuentra frases:
// - Limpia la base de datos local.
// - Inserta las nuevas frases en la base de datos.
// Si no hay frases en la API, carga las frases desde la base local.

class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {

    // Operador invoke permite usar la clase como si fuera una función
    suspend operator fun invoke(): List<Quote> {
        val quotes = repository.getAllQuotesFromApi() // Pide las frases desde la API
        return if (quotes.isNotEmpty()) {
            repository.insertQuotes(quotes.map { it.toDatabase() }) // Guarda las nuevas frases desde la API
            quotes // Devuelve las frases traídas desde la API
        } else {
            repository.getAllQuotesFromDatabase() // Si no hay frases nuevas, devuelve las de la base local
        }
    }
}
