package com.javimutis.examplemvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimutis.examplemvvm.domain.GetQuotesUseCase
import com.javimutis.examplemvvm.domain.GetRandomQuoteUseCase
import com.javimutis.examplemvvm.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
// Anotación que indica que Hilt va a encargarse de la inyección de dependencias en este ViewModel.
@HiltViewModel
class QuoteViewModel @Inject constructor(
    // Inyectamos los casos de uso que este ViewModel necesita para funcionar.
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    // LiveData que contiene una cita (Quote). Esta será observada por la interfaz de usuario.
    val quoteModel = MutableLiveData<Quote>()

    // LiveData que indica si se está cargando información, útil para mostrar un ProgressBar.
    val isLoading = MutableLiveData<Boolean>()

    // Esta función se llama al iniciar la pantalla (por ejemplo, en onCreate de la actividad o fragmento).
    fun onCreate() {
        // Lanzamos una corrutina para ejecutar código asincrónico sin bloquear el hilo principal.
        viewModelScope.launch {
            // Mostramos el indicador de carga (por ejemplo, un spinner).
            isLoading.postValue(true)

            // Ejecutamos el caso de uso que intenta obtener citas desde la API o la base de datos.
            val result = getQuotesUseCase()

            // Si se obtuvo una lista de citas...
            if (result.isNotEmpty()) {
                // Mostramos la primera cita en pantalla.
                quoteModel.postValue(result.first())
                // Ocultamos el indicador de carga.
                isLoading.postValue(false)
            }
        }
    }

    // Esta función permite mostrar una cita aleatoria desde la base de datos local.
    fun randomQuote() {
        // Lanzamos una corrutina para obtener la cita sin bloquear la interfaz.
        viewModelScope.launch {
            // Ejecutamos el caso de uso que devuelve una cita aleatoria.
            val quote = getRandomQuoteUseCase()
            // Si la cita no es nula, la mostramos en pantalla.
            quote?.let {
                quoteModel.postValue(it)
            }
        }
    }
}