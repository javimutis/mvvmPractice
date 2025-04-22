package com.javimutis.examplemvvm.data.model

// Clase que actúa como una fuente temporal de datos.
// Aquí se almacenarán las citas descargadas desde internet.
class QuoteProvider {
    companion object {
        // Lista de citas que se puede compartir globalmente dentro de la app.
        var quotes: List<QuoteModel> = emptyList()
    }
}
