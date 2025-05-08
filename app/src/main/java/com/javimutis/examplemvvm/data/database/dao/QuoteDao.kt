package com.javimutis.examplemvvm.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.javimutis.examplemvvm.data.database.entities.QuoteEntity
import kotlinx.coroutines.flow.Flow

// Esta interfaz indica cómo acceder a los datos en la base de datos Room.
// DAO significa Data Access Object.
@Dao
interface QuoteDao {

    // Obtiene todas las frases de la base de datos, ordenadas por autor de Z a A.
    @Query("SELECT * FROM quote_table ORDER BY author DESC")
    suspend fun getAllQuotes(): List<QuoteEntity>

    // Inserta una frase. Si ya existe (mismo texto), la ignora.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quote: QuoteEntity)

    // Inserta una lista de frases. Las duplicadas se ignoran.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(quotes: List<QuoteEntity>)

    // Borra todas las frases de la base de datos.
    @Query("DELETE FROM quote_table")
    suspend fun deleteAllQuotes()

    // Actualiza una frase existente (sirve para cambiar el autor o favorito).
    @Update
    suspend fun updateQuote(quote: QuoteEntity)

    // Busca una frase específica por su texto. Si la encuentra, la devuelve.
    @Query("SELECT * FROM quote_table WHERE quote = :quoteText LIMIT 1")
    suspend fun getQuoteByText(quoteText: String): QuoteEntity?

    // Devuelve todas las frases que están marcadas como favoritas (en tiempo real con Flow).
    @Query("SELECT * FROM quote_table WHERE isFavorite = 1")
    fun getFavoriteQuotes(): Flow<List<QuoteEntity>>
}
