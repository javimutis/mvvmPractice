package com.javimutis.examplemvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.data.database.entities.toDatabase
import com.javimutis.examplemvvm.domain.GetQuotesUseCase
import com.javimutis.examplemvvm.domain.GetRandomQuoteUseCase
import com.javimutis.examplemvvm.domain.SetFavoriteQuoteUseCase
import com.javimutis.examplemvvm.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel: conecta la lógica de negocio (casos de uso) con la interfaz (UI).
@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,                   // Caso de uso para obtener todas las frases
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,         // Caso de uso para obtener una frase random
    private val setFavoriteQuoteUseCase: SetFavoriteQuoteUseCase,     // Caso de uso para marcar/desmarcar favorito
    private val quoteRepository: QuoteRepository                      // Repositorio de acceso a datos
) : ViewModel() {

    val quoteModel = MutableLiveData<Quote>()      // Frase actual que se muestra en la pantalla
    val isLoading = MutableLiveData<Boolean>()     // Estado de carga (true/false)

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)   // Mostramos el indicador de carga

            val localQuotes = quoteRepository.getAllQuotesFromDatabase()  // Buscamos en base local

            if (localQuotes.isNotEmpty()) {
                quoteModel.postValue(localQuotes.random())  // Si hay, mostramos una random
            } else {
                val apiQuotes = quoteRepository.getAllQuotesFromApi()  // Si no hay, pedimos al API
                quoteRepository.insertQuotes(apiQuotes.map { it.toDatabase() })  // Guardamos en local
                quoteModel.postValue(apiQuotes.random())  // Mostramos una random
            }

            isLoading.postValue(false)  // Ocultamos el indicador de carga
        }
    }

    fun toggleFavorite() {
        quoteModel.value?.let { currentQuote ->
            val newFavoriteStatus = !currentQuote.isFavorite  // Invertimos favorito/no favorito
            val updatedQuote = currentQuote.copy(isFavorite = newFavoriteStatus)  // Creamos copia actualizada
            quoteModel.value = updatedQuote  // Actualizamos la UI

            viewModelScope.launch {
                setFavoriteQuoteUseCase(currentQuote, newFavoriteStatus)  // Guardamos en base de datos
            }
        }
    }

    fun randomQuote() {
        viewModelScope.launch {
            val quote = getRandomQuoteUseCase()  // Pedimos frase random al caso de uso
            quote?.let {
                quoteModel.postValue(it)         // Si hay resultado, actualizamos la UI
            }
        }
    }
}
