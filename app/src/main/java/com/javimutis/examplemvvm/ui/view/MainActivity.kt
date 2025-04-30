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

    private lateinit var binding: ActivityMainBinding // Se usa para acceder al layout sin usar findViewById.

    // Obtenemos el ViewModel que tiene los datos que la vista va a mostrar.
    private val quoteViewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge() // Permite que el contenido use todo el espacio disponible (bajo la barra de estado).

        binding = ActivityMainBinding.inflate(layoutInflater) // Conectamos el XML con Kotlin.
        setContentView(binding.root)

        // Esto evita que el contenido se esconda bajo la barra superior del celular.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Le decimos al ViewModel que cargue la primera cita.
        quoteViewModel.onCreate()

        // Si cambia la cita en el ViewModel, actualizamos el texto en pantalla.
        quoteViewModel.quoteModel.observe(this, Observer { currentQuote ->
            binding.tvQuote.text = currentQuote.quote
            binding.tvAuthor.text = currentQuote.author

            if(currentQuote.isFavorite){
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite)
            }else{
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border)
            }

        })

        // Si el ViewModel está cargando, mostramos un spinner (loader).
        quoteViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        // Si se toca la pantalla, se pide una cita aleatoria.
        binding.viewContainer.setOnClickListener { quoteViewModel.randomQuote() }



        binding.favoriteButton.setOnClickListener {
            quoteViewModel.toggleFavorite()
        }

    }
}
