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
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        binding.progressBar.visibility = View.VISIBLE
        observeFavorites()
    }


    private fun initUI() {
        adapter = FavoriteQuotesAdapter()
        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteQuotesViewModel.favoriteQuotes.collectLatest { favorites ->
                    binding.progressBar.visibility = View.GONE
                    if (favorites.isEmpty()) {
                        binding.rvFavorites.visibility = View.GONE
                        binding.tvEmptyMessage.visibility = View.VISIBLE
                    } else {
                        binding.rvFavorites.visibility = View.VISIBLE
                        binding.tvEmptyMessage.visibility = View.GONE
                        adapter.submitList(favorites)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}