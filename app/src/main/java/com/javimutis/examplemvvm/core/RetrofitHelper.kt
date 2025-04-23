package com.javimutis.examplemvvm.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Este objeto (singleton) sirve para crear una única instancia de Retrofit en toda la app.
// Retrofit se usa para hacer solicitudes HTTP (como conectarse a un servidor web y pedir datos).
object RetrofitHelper {

    // Esta función devuelve una instancia de Retrofit ya configurada.
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            // Aquí le decimos a Retrofit cuál es la dirección base de la API (en este caso, Firebase).
            .baseUrl("https://drawsomething-59328-default-rtdb.europe-west1.firebasedatabase.app/")
            // Esta línea indica que los datos JSON que vienen de internet se van a convertir a objetos Kotlin.
            .addConverterFactory(GsonConverterFactory.create())
            // Finalmente, construimos la instancia de Retrofit y la devolvemos.
            .build()
    }
}
