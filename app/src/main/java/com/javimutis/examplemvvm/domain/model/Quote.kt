package com.javimutis.examplemvvm.domain.model

import com.javimutis.examplemvvm.data.database.entities.QuoteEntity
import com.javimutis.examplemvvm.data.model.QuoteModel

// Este es el modelo que usa la lógica del negocio.
// No depende de cómo vienen los datos ni de cómo se guardan.
data class Quote(val quote: String, val author: String)

// Estas funciones convierten los datos desde otros modelos hacia este.
fun QuoteModel.toDomain() = Quote(quote, author)
fun QuoteEntity.toDomain() = Quote(quote, author)
