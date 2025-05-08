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
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Habilitamos pantalla completa sin bordes
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Si es la primera vez (no hay savedInstanceState), mostramos QuoteFragment por defecto
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.viewContainer.id, QuoteFragment())
                .commit()
        }

        // Configuramos la navegación inferior (BottomNavView)
        binding.bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navQuotesFragment -> {
                    // Navegamos al fragmento de frases
                    supportFragmentManager.beginTransaction()
                        .replace(binding.viewContainer.id, QuoteFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.navFavoritesFragment -> {
                    // Navegamos al fragmento de favoritos
                    supportFragmentManager.beginTransaction()
                        .replace(binding.viewContainer.id, FavoriteQuotesFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }

        // Ajustamos el padding según las barras del sistema (barra superior e inferior)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
