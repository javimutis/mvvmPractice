package com.javimutis.examplemvvm.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.javimutis.examplemvvm.data.database.entities.QuoteEntity

// Esta interfaz indica cómo acceder a los datos en la base de datos Room.
// DAO significa Data Access Object.
@Dao
interface QuoteDao {

    // Obtiene todas las frases guardadas en la base de datos, ordenadas por autor (de Z a A).
    @Query("SELECT * FROM quote_table ORDER BY author DESC")
    suspend fun getAllQuotes(): List<QuoteEntity>

    // Inserta una sola frase en la base de datos. Si ya existe (por clave primaria), la ignora.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quote: QuoteEntity)

    // Inserta una lista de frases en la base de datos, ignorando las que ya existen.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(quotes: List<QuoteEntity>)

    // Borra todas las frases de la base de datos.
    @Query("DELETE FROM quote_table")
    suspend fun deleteAllQuotes()

    // Actualiza una frase ya existente (por ejemplo, para cambiar si es favorita).
    @Update
    suspend fun updateQuote(quote: QuoteEntity)

    // Busca una frase específica por su texto. Si existe, la devuelve.
    @Query("SELECT * FROM quote_table WHERE quote = :quoteText LIMIT 1")
    suspend fun getQuoteByText(quoteText: String): QuoteEntity?
}