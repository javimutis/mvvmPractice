package com.javimutis.examplemvvm.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.javimutis.examplemvvm.R
import com.javimutis.examplemvvm.databinding.FragmentQuoteBinding
import com.javimutis.examplemvvm.ui.viewmodel.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteFragment : Fragment() {

    private var _binding: FragmentQuoteBinding? = null
    private val binding get() = _binding!!

    private val quoteViewModel: QuoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quoteViewModel.onCreate()  // Pedimos que cargue frases

        // Observamos los cambios en la frase actual
        quoteViewModel.quoteModel.observe(viewLifecycleOwner, Observer { currentQuote ->
            binding.tvQuote.text = currentQuote.quote
            binding.tvAuthor.text = currentQuote.author

            // Mostramos el ícono correcto según si es favorito o no
            if (currentQuote.isFavorite) {
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border)
            }
        })

        // Observamos si está cargando para mostrar/ocultar el progreso
        quoteViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progress.isVisible = it
        })

        // Cuando tocamos el fondo, pedimos una frase random
        binding.viewContainer.setOnClickListener {
            quoteViewModel.randomQuote()
        }

        // Cuando tocamos el botón de favorito, cambiamos su estado
        binding.favoriteButton.setOnClickListener {
            quoteViewModel.toggleFavorite()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Evitamos fugas de memoria
    }
}
