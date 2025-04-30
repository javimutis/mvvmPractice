package com.javimutis.examplemvvm.di

import android.content.Context
import androidx.room.Room
import com.javimutis.examplemvvm.data.database.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Módulo de Hilt para indicar cómo crear la base de datos Room.
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val QUOTE_DATABASE_NAME = "quote_database"

    // Crea la base de datos Room.
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, QuoteDatabase::class.java, QUOTE_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    // Proporciona el DAO para acceder a la base de datos.
    @Singleton
    @Provides
    fun provideQuoteDao(db: QuoteDatabase) = db.getQuoteDao()
}