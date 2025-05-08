package com.javimutis.examplemvvm.domain.model

import com.javimutis.examplemvvm.data.database.entities.QuoteEntity
import com.javimutis.examplemvvm.data.model.QuoteModel

// Este es el modelo principal de la app, usado en la lógica del negocio (capa dominio).
// Aquí no nos importa cómo vienen los datos (API) ni cómo se guardan (base de datos).
data class Quote(
    val quote: String,              // El texto de la frase.
    val author: String,             // El autor de la frase.
    val isFavorite: Boolean = false // Indica si la frase es favorita (por defecto es false).
)


// Convierte una frase que viene de la API (QuoteModel) al modelo principal (Quote).
fun QuoteModel.toDomain() = Quote(
    quote = this.quote,        // Copia el texto de la frase.
    author = this.author,      // Copia el autor.
    isFavorite = false         // No se considera el favorito de la API (lo dejamos en false siempre).
)


// Convierte una frase guardada en la base de datos (QuoteEntity) al modelo principal (Quote).
fun QuoteEntity.toDomain() = Quote(
    quote = quote,               // Copia el texto de la frase.
    author = author,             // Copia el autor.
    isFavorite = isFavorite      // Copia el estado favorito desde la base local.
)
