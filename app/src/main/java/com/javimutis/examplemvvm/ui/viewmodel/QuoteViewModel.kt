package com.javimutis.examplemvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.data.database.entities.toDatabase
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
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val quoteRepository: QuoteRepository
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

            val localQuotes = quoteRepository.getAllQuotesFromDatabase()

            if(localQuotes.isNotEmpty()) {
                quoteModel.postValue(localQuotes.random())
            }else {
                val apiQuotes = quoteRepository.getAllQuotesFromApi()
                quoteRepository.insertQuotes(apiQuotes.map { it.toDatabase() })
                quoteModel.postValue(apiQuotes.random())
            }
                // Ocultamos el indicador de carga.
                isLoading.postValue(false)

        }
    }
    fun toggleFavorite() {
        quoteModel.value?.let { currentQuote ->
            val updatedQuote = currentQuote.copy(isFavorite = !currentQuote.isFavorite)
            quoteModel.value = updatedQuote

            viewModelScope.launch {
                quoteRepository.updateQuoteFavoriteStatus(updatedQuote)
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