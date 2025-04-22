package com.javimutis.examplemvvm.ui.view

// Importamos clases necesarias.
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

// Pantalla principal de la app.
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // Vincula el XML con el código Kotlin.

    private val quoteViewModel: QuoteViewModel by viewModels() // Se obtiene el ViewModel de la vista.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge() // Hace que la vista use toda la pantalla, incluyendo la barra de estado.

        // Inflamos (conectamos) el layout XML con el binding.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajusta márgenes para evitar que la vista quede bajo la barra del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Llama a la función del ViewModel que carga la primera cita.
        quoteViewModel.onCreate()

        // Observamos la cita actual: si cambia, actualizamos la UI.
        quoteViewModel.quoteModel.observe(this, Observer { currentQuote ->
            binding.tvQuote.text = currentQuote.quote
            binding.tvAuthor.text = currentQuote.author
        })

        // Observamos si está cargando: mostramos u ocultamos el "loader".
        quoteViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        // Si el usuario toca la pantalla, se muestra una cita aleatoria.
        binding.viewContainer.setOnClickListener { quoteViewModel.randomQuote() }
    }
}
