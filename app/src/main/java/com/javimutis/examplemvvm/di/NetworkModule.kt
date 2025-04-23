package com.javimutis.examplemvvm.di

import com.javimutis.examplemvvm.data.network.QuoteApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            // URL base donde están los datos (en este caso, un Firebase).
            .baseUrl("https://drawsomething-59328-default-rtdb.europe-west1.firebasedatabase.app/")
            // Indica que usaremos GSON para convertir datos JSON a objetos Kotlin.
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Crea y devuelve la instancia final de Retrofit.
    }
    @Singleton
    @Provides
    fun provideQuoteApiClient(retrofit: Retrofit): QuoteApiClient {
        return retrofit.create(QuoteApiClient::class.java)
    }
}