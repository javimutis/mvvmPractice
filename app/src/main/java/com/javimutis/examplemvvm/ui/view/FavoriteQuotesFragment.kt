package com.javimutis.examplemvvm.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.javimutis.examplemvvm.databinding.FragmentFavoritesBinding
import com.javimutis.examplemvvm.ui.adapter.FavoriteQuotesAdapter
import com.javimutis.examplemvvm.ui.viewmodel.FavoriteQuotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoriteQuotesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val favoriteQuotesViewModel: FavoriteQuotesViewModel by viewModels()
    private lateinit var adapter: FavoriteQuotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflamos el layout del fragmento
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()  // Inicializamos la interfaz (RecyclerView, Adapter)
        binding.progressBar.visibility = View.VISIBLE  // Mostramos la barra de carga
        observeFavorites()  // Empezamos a escuchar los cambios en favoritos
    }

    private fun initUI() {
        adapter = FavoriteQuotesAdapter()  // Creamos el adapter
        binding.rvFavorites.adapter = adapter  // Lo conectamos al RecyclerView
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())  // Ponemos un layout lineal (lista vertical)
    }

    private fun observeFavorites() {
        // Escuchamos los cambios en el ViewModel usando coroutines
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteQuotesViewModel.favoriteQuotes.collectLatest { favorites ->
                    binding.progressBar.visibility = View.GONE  // Ocultamos la barra de carga
                    if (favorites.isEmpty()) {
                        binding.rvFavorites.visibility = View.GONE
                        binding.tvEmptyMessage.visibility = View.VISIBLE  // Mostramos mensaje vacío
                    } else {
                        binding.rvFavorites.visibility = View.VISIBLE
                        binding.tvEmptyMessage.visibility = View.GONE
                        adapter.submitList(favorites)  // Mandamos la lista al adapter para que la dibuje
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Evitamos fugas de memoria
    }
}
