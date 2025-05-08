package com.javimutis.examplemvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimutis.examplemvvm.domain.GetFavoriteQuoteUseCase
import com.javimutis.examplemvvm.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteQuotesViewModel @Inject constructor(
    private val getFavoriteQuoteUseCase: GetFavoriteQuoteUseCase  // Caso de uso para obtener favoritos
) : ViewModel() {

    private val _favoriteQuotes = MutableStateFlow<List<Quote>>(emptyList())  // Estado interno (mutable)
    val favoriteQuotes: StateFlow<List<Quote>> = _favoriteQuotes             // Estado expuesto (solo lectura)

    private var favoritesJob: Job? = null  // Referencia al Job para poder cancelarlo si es necesario

    init {
        // Al crear el ViewModel, cargamos los favoritos automáticamente
        refreshFavorites()
    }

    fun refreshFavorites() {
        // Cancelamos colección previa para no tener múltiples escuchas al mismo tiempo
        favoritesJob?.cancel()
        favoritesJob = viewModelScope.launch {
            getFavoriteQuoteUseCase().collectLatest { quotes ->
                println("FavoriteQuotesViewModel: Fetched favorites: ${quotes.size}")
                _favoriteQuotes.value = quotes  // Actualizamos el flujo
            }
        }
    }
}
