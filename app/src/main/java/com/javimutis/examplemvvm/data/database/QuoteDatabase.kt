package com.javimutis.examplemvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javimutis.examplemvvm.data.database.dao.QuoteDao
import com.javimutis.examplemvvm.data.database.entities.QuoteEntity

// Esta clase define la base de datos Room.
@Database(entities = [QuoteEntity::class], version = 3, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {

    // Función para acceder al DAO y así poder operar sobre la base de datos.
    abstract fun getQuoteDao(): QuoteDao
}
