package com.javimutis.examplemvvm.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Objeto singleton que crea y configura una instancia de Retrofit.
// Retrofit nos permite hacer peticiones HTTP (como obtener datos de internet).
object RetrofitHelper {

    // Esta función devuelve una instancia ya configurada de Retrofit.
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            // URL base donde están los datos (en este caso, un Firebase).
            .baseUrl("https://drawsomething-59328-default-rtdb.europe-west1.firebasedatabase.app/")
            // Indica que usaremos GSON para convertir datos JSON a objetos Kotlin.
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Crea y devuelve la instancia final de Retrofit.

    }
}
