package com.javimutis.examplemvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.domain.GetQuoteUseCase
import com.javimutis.examplemvvm.domain.GetRandomQuoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
// Este ViewModel maneja los datos que la vista necesita mostrar.
class QuoteViewModel @Inject constructor(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    val quoteModel = MutableLiveData<QuoteModel>() // Donde guardamos la cita actual.
    val isLoading = MutableLiveData<Boolean>() // Bandera para saber si estamos cargando datos.

    // Esta función se llama al iniciar la vista. Carga la primera cita.
    fun onCreate() {
        viewModelScope.launch { // Ejecuta esta parte en segundo plano.
            isLoading.postValue(true) // Mostramos el loader.
            val result = getQuoteUseCase() // Llamamos al caso de uso que obtiene citas.

            if (!result.isNullOrEmpty()) {
                quoteModel.postValue(result[0]) // Mostramos la primera cita de la lista.
                isLoading.postValue(false) // Ocultamos el loader.
            }
        }
    }

    // Esta función se llama cuando el usuario toca la pantalla para ver otra cita.
    fun randomQuote() {
        isLoading.postValue(true)
        val quote = getRandomQuoteUseCase() // Se pide una cita aleatoria.
        if (quote != null) {
            quoteModel.postValue(quote) // Mostramos esa cita.
        }
        isLoading.postValue(false)
    }
}
