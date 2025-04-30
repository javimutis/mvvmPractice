package com.javimutis.examplemvvm.domain.model

import com.javimutis.examplemvvm.data.database.entities.QuoteEntity
import com.javimutis.examplemvvm.data.model.QuoteModel

// Este es el modelo que usa la lógica del negocio.
// No depende de cómo vienen los datos ni de cómo se guardan.
data class Quote(
    val quote: String,
    val author: String,
    val isFavorite: Boolean = false // Por defecto no es favorita.
)

// Conversión desde la API al modelo de dominio (no guarda favorito aún).
fun QuoteModel.toDomain() = Quote(
    quote = this.quote,
    author = this.author,
    isFavorite = false // Se ignora el valor original por ahora.
)

// Conversión desde la base de datos al modelo de dominio.
fun QuoteEntity.toDomain() = Quote(
    quote = quote,
    author = author,
    isFavorite = isFavorite
)