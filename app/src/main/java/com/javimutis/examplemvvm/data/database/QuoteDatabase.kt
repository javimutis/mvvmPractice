package com.javimutis.examplemvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javimutis.examplemvvm.data.database.dao.QuoteDao
import com.javimutis.examplemvvm.data.database.entities.QuoteEntity

// Esta clase define la base de datos Room.
@Database(entities = [QuoteEntity::class], version = 3)
abstract class QuoteDatabase : RoomDatabase() {

    // Aquí decimos que usaremos el DAO que definimos antes.
    abstract fun getQuoteDao(): QuoteDao
}
