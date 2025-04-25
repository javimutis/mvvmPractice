package com.javimutis.examplemvvm.data.model

import com.google.gson.annotations.SerializedName

// Representa cómo viene una frase desde la API (formato JSON).
data class QuoteModel(
    @SerializedName("quote") val quote: String,
    @SerializedName("author") val author: String
)
