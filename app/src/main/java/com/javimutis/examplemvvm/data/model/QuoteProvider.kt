package com.javimutis.examplemvvm.data.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
// Clase que actúa como una fuente temporal de datos.
// Aquí se almacenarán las citas descargadas desde internet.
class QuoteProvider @Inject constructor() {
    // Lista de citas que se puede compartir globalmente dentro de la app.
    var quotes: List<QuoteModel> = emptyList()
}

