package com.javimutis.examplemvvm.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.GetQuotesUseCase
import com.javimutis.examplemvvm.domain.GetRandomQuoteUseCase
import com.javimutis.examplemvvm.domain.SetFavoriteQuoteUseCase
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuoteViewModelTest {

    // Creamos mocks relajados (no necesitamos configurar todas las respuestas).
    @RelaxedMockK
    private lateinit var getQuotesUseCase: GetQuotesUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @RelaxedMockK
    private lateinit var setFavoriteQuoteUseCase: SetFavoriteQuoteUseCase

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    // Instancia del ViewModel a probar.
    @RelaxedMockK
    private lateinit var quoteViewModel: QuoteViewModel

    // Esta regla permite ejecutar LiveData de manera sincronizada durante los tests.
    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        // Inicializamos los mocks y configuramos el ViewModel antes de cada prueba.
        MockKAnnotations.init(this)
        // Inyectamos las dependencias en el ViewModel.
        quoteViewModel = QuoteViewModel(
            getQuotesUseCase, getRandomQuoteUseCase, setFavoriteQuoteUseCase, quoteRepository
        )

        // Configuramos un Dispatcher especial para pruebas (para no usar el hilo principal real).
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        // Restauramos el Dispatcher original después de cada prueba.
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is created at the first time, get all quotes and set the first value`() =
        runTest {
            // Given: configuramos una lista de citas simuladas
            val quoteList = listOf(
                Quote("Cita1", "Autor1"),
                Quote("Cita2", "Autor2")
            )
            coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList

            // When: llamamos a la función que se ejecuta al iniciar el ViewModel
            quoteViewModel.onCreate()

            // Then: verificamos que la cita mostrada sea la primera de la lista
            assert(quoteViewModel.quoteModel.value in quoteList)
        }

    @Test
    fun `when randomQuoteUseCase return a quote set on the livedata`() = runTest {
        // Given: configuramos que el caso de uso devuelva una cita simulada
        val quote = Quote("Cita1", "Autor1")
        coEvery { getRandomQuoteUseCase() } returns quote

        // When: llamamos a la función para obtener una cita aleatoria
        quoteViewModel.randomQuote()

        // Then: verificamos que la cita mostrada sea la simulada
        assert(quoteViewModel.quoteModel.value == quote)
    }

    @Test
    fun `if randomQuoteUseCase return null, keep the last value`() = runTest {
        // Given: hay una cita ya cargada
        val quote = Quote("Cita1", "Autor1")
        quoteViewModel.quoteModel.value = quote

        // Configuramos que el caso de uso devuelva null
        coEvery { getRandomQuoteUseCase() } returns null

        // When: llamamos a la función para obtener una cita aleatoria
        quoteViewModel.randomQuote()

        // Then: verificamos que la cita no cambie
        assert(quoteViewModel.quoteModel.value == quote)
    }

    @Test
    fun `when no local quotes, get all quotes from API and set one`() = runTest {
        // Given: no hay datos locales
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

        // Datos simulados desde la API
        val apiQuotes = listOf(
            Quote("ApiCita1", "ApiAutor1"),
            Quote("ApiCita2", "ApiAutor2")
        )
        coEvery { quoteRepository.getAllQuotesFromApi() } returns apiQuotes

        // Cuando se intenta insertar, simulamos vacío (Unit)
        coEvery { quoteRepository.insertQuotes(any()) } returns Unit

        // When: llamamos a la función de inicio
        quoteViewModel.onCreate()

        // Then: verificamos que la cita mostrada sea una de las de API (también random)
        assert(quoteViewModel.quoteModel.value in apiQuotes)
    }
}