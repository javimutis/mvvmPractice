package com.javimutis.examplemvvm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.javimutis.examplemvvm.databinding.ItemFavoritesBinding
import com.javimutis.examplemvvm.domain.model.Quote

class FavoriteQuotesAdapter :
    ListAdapter<Quote, FavoriteQuotesViewHolder>(QuoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteQuotesViewHolder {
        val binding = ItemFavoritesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteQuotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteQuotesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class QuoteDiffCallback : DiffUtil.ItemCallback<Quote>() {
    override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
        return oldItem.quote == newItem.quote
    }

    override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
        return oldItem == newItem
    }
}
