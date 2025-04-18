package com.javimutis.examplemvvm.data.model

import com.google.gson.annotations.SerializedName

// Esta clase representa una cita. Contiene el texto y el autor.
data class QuoteModel(
    @SerializedName("quote") val quote: String,
    @SerializedName("author") val author: String
)
