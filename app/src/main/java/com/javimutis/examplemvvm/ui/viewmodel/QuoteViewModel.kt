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

@HiltViewModel // Indica que este ViewModel será gestionado por Hilt (inyección de dependencias).
class QuoteViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    // Esta LiveData contiene la cita que se mostrará en pantalla.
    val quoteModel = MutableLiveData<Quote>()

    // Esta LiveData indica si se está cargando la información (para mostrar el ProgressBar).
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true) // Muestra el indicador de carga.

            val result = getQuotesUseCase() // Llama al caso de uso para obtener citas.

            if (result.isNotEmpty()) {
                quoteModel.postValue(result.first()) // Muestra la primera cita recibida.
                isLoading.postValue(false) // Oculta el indicador de carga.
            }
        }
    }


    // Esta función obtiene una cita aleatoria desde la base de datos local.
    fun randomQuote() {
        viewModelScope.launch {
            val quote = getRandomQuoteUseCase() // Llama al caso de uso para obtener una cita aleatoria.
            quote?.let {
                quoteModel.postValue(it) // Si hay una cita, se muestra.
            }
        }
    }
}