package com.javimutis.examplemvvm.data.model

import com.google.gson.annotations.SerializedName

// Representa cómo viene una frase desde la API (formato JSON).
data class QuoteModel(
    @SerializedName("quote") val quote: String,        // Texto de la frase (como viene de la API).
    @SerializedName("author") val author: String,      // Autor de la frase.
    @SerializedName("isFavorite") val isFavorite: Boolean // Estado favorito desde la API (no se usa por ahora, pero está listo).
)
