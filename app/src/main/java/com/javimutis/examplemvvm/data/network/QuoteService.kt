package com.javimutis.examplemvvm.data.network

import com.javimutis.examplemvvm.core.RetrofitHelper
import com.javimutis.examplemvvm.data.model.QuoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getQuotes(): List<QuoteModel> {
        return withContext(Dispatchers.IO) { // Se ejecuta en un hilo secundario para no bloquear el hilo principal.
            val response = retrofit.create(QuoteApiClient::class.java).getAllQuotes()
            response.body() ?: emptyList()
        }
    }
}