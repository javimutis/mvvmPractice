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

//Aquí probamos que el ViewModel se comporte correctamente al inicializarse, al pedir citas aleatorias, y al manejar escenarios con o sin datos locales.
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

    // ViewModel a probar
    @RelaxedMockK
    private lateinit var quoteViewModel: QuoteViewModel

    // Permite que LiveData se ejecute instantáneamente en tests (sin esperar al hilo principal)
    @get:Rule val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this) // Inicializa los mocks
        quoteViewModel = QuoteViewModel( // Inyectamos dependencias al ViewModel
            getQuotesUseCase, getRandomQuoteUseCase, setFavoriteQuoteUseCase, quoteRepository
        )
        Dispatchers.setMain(Dispatchers.Unconfined) // Usamos un dispatcher de prueba
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain() // Restauramos el dispatcher original después de cada test
    }
    @Test
    fun `when view model is created at the first time, get all quotes and set the first value`() = runTest {
        // Given: Creamos una lista simulada de citas
        val quoteList = listOf(Quote("Cita1", "Autor1"), Quote("Cita2", "Autor2"))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList

        // When: Simulamos que el ViewModel se inicializa
        quoteViewModel.onCreate()

        // Then: Verificamos que LiveData contiene alguna de las citas de la lista (normalmente la primera)
        assert(quoteViewModel.quoteModel.value in quoteList)
    }

    @Test
    fun `when randomQuoteUseCase return a quote set on the livedata`() = runTest {
        // Given: Configuramos que el caso de uso devuelva una cita específica
        val quote = Quote("Cita1", "Autor1")
        coEvery { getRandomQuoteUseCase() } returns quote

        // When: Llamamos a la función que obtiene una cita aleatoria
        quoteViewModel.randomQuote()

        // Then: Verificamos que LiveData ahora contiene esa cita
        assert(quoteViewModel.quoteModel.value == quote)
    }

    @Test
    fun `if randomQuoteUseCase return null, keep the last value`() = runTest {
        // Given: Tenemos una cita ya cargada en LiveData
        val quote = Quote("Cita1", "Autor1")
        quoteViewModel.quoteModel.value = quote

        // Configuramos que el caso de uso devuelva null (no hay nueva cita)
        coEvery { getRandomQuoteUseCase() } returns null

        // When: Llamamos a randomQuote()
        quoteViewModel.randomQuote()

        // Then: Verificamos que LiveData mantiene la misma cita, no cambia
        assert(quoteViewModel.quoteModel.value == quote)
    }

    @Test
    fun `when no local quotes, get all quotes from API and set one`() = runTest {
        // Given: Simulamos que no hay datos locales
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

        // Simulamos obtener citas desde la API
        val apiQuotes = listOf(Quote("ApiCita1", "ApiAutor1"), Quote("ApiCita2", "ApiAutor2"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns apiQuotes

        // Simulamos que insertar en la base de datos no hace nada (Unit)
        coEvery { quoteRepository.insertQuotes(any()) } returns Unit

        // When: Llamamos a la función de inicio
        quoteViewModel.onCreate()

        // Then: Verificamos que LiveData contiene alguna cita de la API
        assert(quoteViewModel.quoteModel.value in apiQuotes)
    }
}