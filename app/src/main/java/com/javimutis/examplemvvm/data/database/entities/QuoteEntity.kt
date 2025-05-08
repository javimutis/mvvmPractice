package com.javimutis.examplemvvm.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javimutis.examplemvvm.domain.model.Quote

// Representa cómo se guarda una frase en la base de datos local (Room).
@Entity(tableName = "quote_table")
data class QuoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "quote") val quote: String,       // Texto de la frase (clave única).
    @ColumnInfo(name = "author") val author: String,    // Nombre del autor.
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean // Si es favorita o no.
)

// Función para convertir una frase del modelo de dominio (Quote) a modelo de base de datos (QuoteEntity).
fun Quote.toDatabase() = QuoteEntity(quote = quote, author = author, isFavorite = isFavorite)
