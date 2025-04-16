package com.javimutis.examplemvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.javimutis.examplemvvm.model.QuoteModel
import com.javimutis.examplemvvm.model.QuoteProvider

class QuoteViewModel : ViewModel(){
    val quoteModel = MutableLiveData<QuoteModel>()

    fun randomQuote(){
        val currentQuote = QuoteProvider.random()
        quoteModel.postValue(currentQuote)
    }

}