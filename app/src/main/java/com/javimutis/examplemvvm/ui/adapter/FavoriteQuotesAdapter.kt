package com.javimutis.examplemvvm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.javimutis.examplemvvm.databinding.ItemFavoritesBinding
import com.javimutis.examplemvvm.domain.model.Quote

// Este adapter conecta la lista de frases favoritas con el RecyclerView que las muestra en pantalla.
class FavoriteQuotesAdapter :
    ListAdapter<Quote, FavoriteQuotesViewHolder>(QuoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteQuotesViewHolder {
        // Inflamos (cargamos) el layout de cada item de la lista (item_favorites.xml)
        val binding = ItemFavoritesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        // Devolvemos un ViewHolder que guarda esa vista inflada
        return FavoriteQuotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteQuotesViewHolder, position: Int) {
        // Cada vez que el RecyclerView necesita mostrar un item, lo vinculamos con los datos de Quote
        holder.bind(getItem(position))
    }
}

// Esta clase le dice al RecyclerView cómo detectar cambios en la lista.
class QuoteDiffCallback : DiffUtil.ItemCallback<Quote>() {
    override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
        // Comparamos si son el mismo item (usamos el texto de la frase como identificador)
        return oldItem.quote == newItem.quote
    }

    override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
        // Comparamos si todo el contenido del item es igual (no solo el ID)
        return oldItem == newItem
    }
}
