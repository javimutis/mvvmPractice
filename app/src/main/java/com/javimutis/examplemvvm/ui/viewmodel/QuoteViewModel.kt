package com.javimutis.examplemvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.domain.GetQuoteUseCase
import com.javimutis.examplemvvm.domain.GetRandomQuoteUseCase
import kotlinx.coroutines.launch

// El ViewModel se encarga de manejar los datos que la vista necesita.
class QuoteViewModel : ViewModel() {

    val quoteModel = MutableLiveData<QuoteModel>() // Cita actual que se mostrará en pantalla.
    val isLoading = MutableLiveData<Boolean>() // Estado de carga: true si está cargando.

    var getQuoteUseCase = GetQuoteUseCase() // Caso de uso para obtener todas las citas.
    var getRandomQuoteUseCase = GetRandomQuoteUseCase() // Caso de uso para obtener una cita aleatoria.

    // Esta función se llama cuando se crea la vista. Carga la primera cita.
    fun onCreate() {
        viewModelScope.launch { // Lanza una corrutina (tarea en segundo plano).
            isLoading.postValue(true) // Muestra el loader.
            val result = getQuoteUseCase() // Pide las citas al caso de uso.

            // Si se obtuvo una lista de citas, se muestra la primera.
            if (!result.isNullOrEmpty()) {
                quoteModel.postValue(result[0])
                isLoading.postValue(false) // Oculta el loader.
            }
        }
    }

    // Esta función se llama al hacer clic y muestra una cita aleatoria.
    fun randomQuote() {
        isLoading.postValue(true)
        val quote = getRandomQuoteUseCase()
        if (quote != null) {
            quoteModel.postValue(quote) // Actualiza la UI con la nueva cita.
        }
        isLoading.postValue(false)
    }
}
