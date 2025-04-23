package com.javimutis.examplemvvm.data.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton // Esta clase existirá como una sola instancia en toda la app.
class QuoteProvider @Inject constructor() {

    // Esta lista guarda temporalmente todas las citas descargadas.
    var quotes: List<QuoteModel> = emptyList()
}
