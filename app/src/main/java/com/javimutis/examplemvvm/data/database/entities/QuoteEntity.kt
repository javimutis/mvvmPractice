package com.javimutis.examplemvvm.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javimutis.examplemvvm.domain.model.Quote

// Representa cómo se guarda una frase en la base de datos local (Room).
@Entity(tableName = "quote_table")
data class QuoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "quote") val quote: String, // El texto de la frase (clave primaria).
    @ColumnInfo(name = "author") val author: String, // El nombre del autor.
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean // Indica si la frase está marcada como favorita.
)

// Convierte una frase del modelo de dominio (Quote) al modelo de base de datos (QuoteEntity).
fun Quote.toDatabase() = QuoteEntity(quote = quote, author = author, isFavorite = isFavorite)
