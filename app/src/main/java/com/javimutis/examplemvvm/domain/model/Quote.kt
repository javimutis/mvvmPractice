package com.javimutis.examplemvvm.domain.model

import com.javimutis.examplemvvm.data.database.entities.QuoteEntity
import com.javimutis.examplemvvm.data.model.QuoteModel

// Este es el modelo que usa la lógica del negocio.
// No depende de cómo vienen los datos ni de cómo se guardan.
data class Quote(
    val quote: String,
    val author: String,
    val isFavorite: Boolean = false
)

// Estas funciones convierten los datos desde otros modelos hacia este.
fun QuoteModel.toDomain() = Quote(
    quote = this.quote,
    author = this.author,
    isFavorite = false
)
fun QuoteEntity.toDomain() = Quote(quote = quote, author = author, isFavorite = isFavorite)
