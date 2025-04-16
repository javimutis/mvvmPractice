package com.javimutis.examplemvvm.viewmodel

import androidx.lifecycle.MutableLiveData // Permite observar datos que cambian.
import androidx.lifecycle.ViewModel
import com.javimutis.examplemvvm.model.QuoteModel
import com.javimutis.examplemvvm.model.QuoteProvider

class QuoteViewModel : ViewModel() {

    // Esta es la cita actual que está siendo mostrada en pantalla.
    val quoteModel = MutableLiveData<QuoteModel>()

    // Esta función elige una cita aleatoria y la "publica" para que la vea la vista.
    fun randomQuote() {
        val currentQuote = QuoteProvider.random()
        quoteModel.postValue(currentQuote) // Actualiza el LiveData con la nueva cita.
    }
}
