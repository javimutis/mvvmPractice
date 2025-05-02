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
    private val getQuotesUseCase: GetQuotesUseCase,                   // Caso de uso para obtener todas las frases.
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,         // Caso de uso para obtener una frase aleatoria.
    private val setFavoriteQuoteUseCase: SetFavoriteQuoteUseCase,     // Caso de uso para marcar como favorito.
    private val quoteRepository: QuoteRepository                      // Repositorio para acceso directo a datos.
) : ViewModel() {

    // LiveData: variables que la interfaz observa para actualizarse automáticamente.
    val quoteModel = MutableLiveData<Quote>()      // La frase actual mostrada en la UI.
    val isLoading = MutableLiveData<Boolean>()     // Indica si la app está cargando datos.

    // Función que se llama al iniciar la pantalla (onCreate de la Activity o Fragment).
    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)   // Activamos el indicador de carga.

            // Buscamos frases en la base de datos local.
            val localQuotes = quoteRepository.getAllQuotesFromDatabase()

            if (localQuotes.isNotEmpty()) {
                // Si hay frases locales, seleccionamos una al azar.
                quoteModel.postValue(localQuotes.random())
            } else {
                // Si no hay frases locales, las pedimos a la API remota.
                val apiQuotes = quoteRepository.getAllQuotesFromApi()

                // Guardamos las frases nuevas en la base local.
                quoteRepository.insertQuotes(apiQuotes.map { it.toDatabase() })

                // Mostramos una frase aleatoria.
                quoteModel.postValue(apiQuotes.random())
            }

            isLoading.postValue(false)  // Desactivamos el indicador de carga.
        }
    }

    // Cambia el estado de favorito de la frase actual cuando el usuario presiona el botón.
    fun toggleFavorite() {
        quoteModel.value?.let { currentQuote ->   // Si hay una frase cargada…
            val newFavoriteStatus = !currentQuote.isFavorite    // Invertimos su estado favorito.

            val updatedQuote = currentQuote.copy(isFavorite = newFavoriteStatus)  // Creamos una copia actualizada.

            quoteModel.value = updatedQuote   // Actualizamos el LiveData → la UI se actualiza.

            viewModelScope.launch {
                // Usamos el caso de uso para actualizar el estado favorito en la base de datos.
                setFavoriteQuoteUseCase(currentQuote, newFavoriteStatus)
            }
        }
    }

    // Obtiene y muestra una frase aleatoria.
    fun randomQuote() {
        viewModelScope.launch {
            val quote = getRandomQuoteUseCase()  // Caso de uso para obtener una frase random.
            quote?.let {
                quoteModel.postValue(it)         // Si hay resultado, actualizamos la UI.
            }
        }
    }

}
