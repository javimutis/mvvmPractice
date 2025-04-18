package com.javimutis.examplemvvm.data

import com.javimutis.examplemvvm.data.model.QuoteModel
import com.javimutis.examplemvvm.data.model.QuoteProvider
import com.javimutis.examplemvvm.data.network.QuoteService

class QuoteRepository {
     private val api = QuoteService()

    suspend fun getAllQuotes():List<QuoteModel>{
        val response = api.getQuotes()
        QuoteProvider.quotes = response
        return response
    }
}