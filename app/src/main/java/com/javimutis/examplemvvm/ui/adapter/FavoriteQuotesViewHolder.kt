package com.javimutis.examplemvvm.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.javimutis.examplemvvm.databinding.ItemFavoritesBinding
import com.javimutis.examplemvvm.domain.model.Quote

class FavoriteQuotesViewHolder(private val binding: ItemFavoritesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(quote: Quote) {
        binding.tvFavQuote.text = quote.quote
        binding.tvFavAuthor.text = quote.author
    }
}
