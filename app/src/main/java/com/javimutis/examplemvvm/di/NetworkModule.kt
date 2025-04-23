package com.javimutis.examplemvvm.di

import com.javimutis.examplemvvm.data.network.QuoteApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Este módulo define cómo crear e "inyectar" objetos que la app necesita.
@Module
@InstallIn(SingletonComponent::class) // Estos objetos vivirán mientras la app esté viva.
object NetworkModule {

    // Provee una instancia de Retrofit.
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://drawsomething-59328-default-rtdb.europe-west1.firebasedatabase.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provee una instancia del cliente de API (que sabe cómo hacer la llamada GET).
    @Singleton
    @Provides
    fun provideQuoteApiClient(retrofit: Retrofit): QuoteApiClient {
        return retrofit.create(QuoteApiClient::class.java)
    }
}
