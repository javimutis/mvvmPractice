package com.javimutis.examplemvvm.data.model

import com.google.gson.annotations.SerializedName

// Modelo de datos que representa una "cita" (frase).
// Tiene dos atributos: la frase (quote) y su autor (author).
data class QuoteModel(
    @SerializedName("quote") val quote: String, // Frase como texto.
    @SerializedName("author") val author: String // Nombre del autor.
)
