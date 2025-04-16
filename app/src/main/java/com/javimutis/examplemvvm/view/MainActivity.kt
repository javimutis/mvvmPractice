package com.javimutis.examplemvvm.view

// Importamos todas las clases necesarias para que funcione nuestra app.
import android.os.Bundle
import androidx.activity.enableEdgeToEdge // Ajusta el contenido para ocupar toda la pantalla.
import androidx.activity.viewModels // Sirve para obtener el ViewModel.
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat // Para trabajar con compatibilidad visual de las vistas.
import androidx.core.view.WindowInsetsCompat // Para manejar márgenes del sistema (barra superior, inferior).
import androidx.lifecycle.Observer // Observa los datos del ViewModel.
import com.javimutis.examplemvvm.R // Referencia a los recursos (colores, strings, layouts, etc).
import com.javimutis.examplemvvm.databinding.ActivityMainBinding // Permite acceder a las vistas sin usar findViewById.
import com.javimutis.examplemvvm.viewmodel.QuoteViewModel // Importa el ViewModel que contiene la lógica de negocio.

class MainActivity : AppCompatActivity() {

    // Variable para manejar el binding de la vista (activity_main.xml)
    private lateinit var binding: ActivityMainBinding

    // Usamos viewModels() para obtener una instancia de nuestro ViewModel
    private val quoteViewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita que la app use toda la pantalla, incluyendo zonas del sistema (como barra de estado).
        enableEdgeToEdge()

        // Inicializamos el binding para poder usar nuestras vistas de forma directa.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Le decimos a Android que esta es la vista que debe mostrar.

        // Ajustamos el padding de la vista principal para que no se oculte contenido por el notch o barra del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Observamos los cambios en la cita actual. Si cambia, actualizamos el texto en pantalla.
        quoteViewModel.quoteModel.observe(this, Observer { currentQuote ->
            binding.tvQuote.text = currentQuote.quote
            binding.tvAuthor.text = currentQuote.author
        })

        // Cuando el usuario hace clic en el contenedor, se genera una nueva cita aleatoria.
        binding.viewContainer.setOnClickListener { quoteViewModel.randomQuote() }
    }
}
