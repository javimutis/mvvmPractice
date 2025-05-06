package com.javimutis.examplemvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimutis.examplemvvm.domain.GetFavoriteQuoteUseCase
import com.javimutis.examplemvvm.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteQuotesViewModel @Inject constructor(
    private val getFavoriteQuoteUseCase: GetFavoriteQuoteUseCase
) : ViewModel() {

    private val _favoriteQuotes = MutableStateFlow<List<Quote>>(emptyList())
    val favoriteQuotes: StateFlow<List<Quote>> = _favoriteQuotes

    init {
        getFavoriteQuotes()
    }

    private fun getFavoriteQuotes() {
        viewModelScope.launch {
            getFavoriteQuoteUseCase().collectLatest { quotes ->
                Log.d("FavoriteQuotesViewModel", "Fetched favorites: ${quotes.size}")
                _favoriteQuotes.value = quotes
            }
        }
    }

}
