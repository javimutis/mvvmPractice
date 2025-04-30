package com.javimutis.examplemvvm.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.javimutis.examplemvvm.R
import com.javimutis.examplemvvm.databinding.ActivityMainBinding
import com.javimutis.examplemvvm.ui.viewmodel.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
// Esta es la pantalla principal de la app (la primera que se ve).
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quoteViewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Se enlaza el XML con el código.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajusta el padding para evitar que la vista se esconda bajo las barras del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Al iniciar, le pedimos al ViewModel que cargue una frase.
        quoteViewModel.onCreate()

        // Observa los cambios de la frase actual.
        quoteViewModel.quoteModel.observe(this, Observer { currentQuote ->
            binding.tvQuote.text = currentQuote.quote
            binding.tvAuthor.text = currentQuote.author

            // Muestra el ícono según si es favorita o no.
            if(currentQuote.isFavorite){
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite)
            }else{
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border)
            }
        })

        // Muestra u oculta el loader mientras se carga una frase.
        quoteViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        // Cambia la frase cuando el usuario toca la pantalla.
        binding.viewContainer.setOnClickListener {
            quoteViewModel.randomQuote()
        }

        // Cambia el estado de favorito al hacer clic en el botón.
        binding.favoriteButton.setOnClickListener {
            quoteViewModel.toggleFavorite()
        }
    }
}

