package com.javimutis.examplemvvm.data.model

import com.google.gson.annotations.SerializedName

// Este es el "molde" o plantilla de cómo luce una cita (frase) que llega desde la API.
data class QuoteModel(
    @SerializedName("quote") val quote: String,
    @SerializedName("author") val author: String
)