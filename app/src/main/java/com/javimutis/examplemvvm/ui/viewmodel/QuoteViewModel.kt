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
// Esta anotación indica que esta clase será administrada por Hilt para inyección de dependencias.
@HiltViewModel
class QuoteViewModel @Inject constructor(
    // Se inyectan los "casos de uso" y el repositorio que esta clase necesita.
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val setFavoriteQuoteUseCase: SetFavoriteQuoteUseCase,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    // LiveData que contiene una cita actual. La interfaz (UI) observa esto para mostrar el contenido.
    val quoteModel = MutableLiveData<Quote>()

    // LiveData para indicar si la app está "cargando" algo (por ejemplo, mientras espera una API).
    val isLoading = MutableLiveData<Boolean>()

    // Función que se llama al iniciar la pantalla. Aquí se cargan las citas por primera vez.
    fun onCreate() {
        // viewModelScope permite lanzar una "corrutina", que ejecuta tareas sin bloquear la interfaz.
        viewModelScope.launch {
            // Mostramos un indicador de carga (por ejemplo, un spinner).
            isLoading.postValue(true)

            // Intentamos obtener las citas guardadas en la base de datos local.
            val localQuotes = quoteRepository.getAllQuotesFromDatabase()

            if(localQuotes.isNotEmpty()) {
                // Si hay citas locales, elegimos una al azar y la mostramos.
                quoteModel.postValue(localQuotes.random())
            } else {
                // Si no hay citas locales, las pedimos a la API (servidor en internet).
                val apiQuotes = quoteRepository.getAllQuotesFromApi()

                // Guardamos esas citas en la base de datos local para usarlas más adelante.
                quoteRepository.insertQuotes(apiQuotes.map { it.toDatabase() })

                // Mostramos una cita aleatoria de las que llegaron desde la API.
                quoteModel.postValue(apiQuotes.random())
            }

            // Ocultamos el indicador de carga.
            isLoading.postValue(false)
        }
    }

    // Esta función se llama cuando el usuario presiona el botón de "favorito".
    fun toggleFavorite() {
        // Si hay una cita actual visible (quoteModel.value), la tomamos.
        quoteModel.value?.let { currentQuote ->
            val newFavoriteStatus = !currentQuote.isFavorite
            // Creamos una copia de la cita actual, cambiando su estado de favorito al contrario.
            val updatedQuote = currentQuote.copy(isFavorite = newFavoriteStatus)

            // Actualizamos la LiveData con la nueva cita (esto hará que la UI se actualice).
            quoteModel.value = updatedQuote

            // Lanzamos una corrutina para actualizar esa cita en la base de datos.
            viewModelScope.launch {
                // Le decimos al repositorio que actualice el valor "isFavorite" de esta cita.
                setFavoriteQuoteUseCase(currentQuote, newFavoriteStatus)
            }
        }
    }


    // Esta función permite mostrar una cita aleatoria cuando el usuario presiona un botón.
    fun randomQuote() {
        // Lanzamos una corrutina para que la operación sea asincrónica.
        viewModelScope.launch {
            // Ejecutamos el caso de uso que obtiene una cita aleatoria desde la base de datos.
            val quote = getRandomQuoteUseCase()

            // Si obtuvimos una cita, la mostramos.
            quote?.let {
                quoteModel.postValue(it)
            }
        }
    }

}