package com.javimutis.examplemvvm.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javimutis.examplemvvm.data.database.entities.QuoteEntity

// Esta interfaz indica cómo acceder a los datos en la base de datos Room.
// DAO significa Data Access Object.
@Dao
interface QuoteDao {

    // Recupera todas las frases ordenadas por autor (de Z a A).
    @Query("SELECT * FROM quote_table ORDER BY author DESC")
    suspend fun getAllQuotes(): List<QuoteEntity>

    // Inserta una lista de frases. Si ya existen, las reemplaza.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<QuoteEntity>)

    // Borra todas las frases guardadas.
    @Query("DELETE FROM quote_table")
    suspend fun deleteAllQuotes()
}
