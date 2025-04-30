package com.javimutis.examplemvvm.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javimutis.examplemvvm.domain.model.Quote

// Representa cómo se guarda una frase en la base de datos local (Room).
@Entity(tableName = "quote_table")
data class QuoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "quote") val quote: String, // El texto de la frase.
    @ColumnInfo(name = "author") val author: String, // El autor de la frase.
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean
)


// Esta función permite convertir una frase del dominio a una entidad para guardar en Room.
fun Quote.toDatabase() = QuoteEntity(quote = quote, author = author, isFavorite = isFavorite)
