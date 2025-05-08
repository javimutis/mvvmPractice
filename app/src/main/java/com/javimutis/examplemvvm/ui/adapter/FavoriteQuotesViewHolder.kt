package com.javimutis.examplemvvm.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.javimutis.examplemvvm.databinding.ItemFavoritesBinding
import com.javimutis.examplemvvm.domain.model.Quote

// Este ViewHolder mantiene la referencia a las vistas del item para mostrar frase y autor.
class FavoriteQuotesViewHolder(private val binding: ItemFavoritesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    // Aquí asignamos los datos del Quote a los TextView del item
    fun bind(quote: Quote) {
        binding.tvFavQuote.text = quote.quote
        binding.tvFavAuthor.text = quote.author
    }
}

