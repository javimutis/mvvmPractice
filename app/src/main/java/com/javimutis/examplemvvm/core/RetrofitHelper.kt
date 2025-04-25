package com.javimutis.examplemvvm.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Objeto que se usará para crear una única instancia de Retrofit.
// Retrofit permite conectarse con una API en internet para obtener o enviar datos.
object RetrofitHelper {

    // Esta función devuelve una instancia configurada de Retrofit.
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            // Dirección base de la API (el dominio donde están los datos).
            .baseUrl("https://drawsomething-59328-default-rtdb.europe-west1.firebasedatabase.app/")
            // Le decimos que convierta las respuestas JSON a objetos Kotlin automáticamente.
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Construimos y devolvemos la instancia.
    }
}

