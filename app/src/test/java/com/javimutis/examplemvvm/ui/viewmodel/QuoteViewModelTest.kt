package com.javimutis.examplemvvm.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.GetQuotesUseCase
import com.javimutis.examplemvvm.domain.GetRandomQuoteUseCase
import com.javimutis.examplemvvm.domain.SetFavoriteQuoteUseCase
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Aquí probamos que el ViewModel se comporte correctamente al inicializarse, al pedir citas aleatorias, y al manejar escenarios con o sin datos locales.
@ExperimentalCoroutinesApi
class QuoteViewModelTest {

    @RelaxedMockK
    private lateinit var getQuotesUseCase: GetQuotesUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @RelaxedMockK
    private lateinit var setFavoriteQuoteUseCase: SetFavoriteQuoteUseCase

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    private lateinit var quoteViewModel: QuoteViewModel

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        // Inicializamos los mocks antes de cada prueba
        MockKAnnotations.init(this)
        // Creamos una instancia del ViewModel con los mocks
        quoteViewModel = QuoteViewModel(
            getQuotesUseCase, getRandomQuoteUseCase, setFavoriteQuoteUseCase, quoteRepository
        )
        // Configuramos el dispatcher principal para las corutinas
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        // Reseteamos el dispatcher principal al finalizar cada prueba
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is created at the first time, get a local or API quote`() = runTest {
        // Simulamos que el repositorio devuelve una lista de citas locales
        val quoteList = listOf(Quote("Cita1", "Autor1"), Quote("Cita2", "Autor2"))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList

        // Llamamos a onCreate()
        quoteViewModel.onCreate()

        // Verificamos que el valor actual del LiveData esté en la lista local
        assertTrue(quoteViewModel.quoteModel.value in quoteList)
    }

    @Test
    fun `when randomQuoteUseCase returns a quote, set on the LiveData`() = runTest {
        // Simulamos que el caso de uso devuelve una cita aleatoria
        val quote = Quote("Cita1", "Autor1")
        coEvery { getRandomQuoteUseCase() } returns quote

        // Llamamos a randomQuote()
        quoteViewModel.randomQuote()

        // Verificamos que el LiveData se actualizó correctamente
        assertEquals(quote, quoteViewModel.quoteModel.value)
    }

    @Test
    fun `if randomQuoteUseCase returns null, keep the last value`() = runTest {
        // Establecemos un valor inicial en el LiveData
        val quote = Quote("Cita1", "Autor1")
        quoteViewModel.quoteModel.value = quote

        // Simulamos que el caso de uso devuelve null
        coEvery { getRandomQuoteUseCase() } returns null

        // Llamamos a randomQuote()
        quoteViewModel.randomQuote()

        // Verificamos que el valor no haya cambiado
        assertEquals(quote, quoteViewModel.quoteModel.value)
    }

    @Test
    fun `when no local quotes, get all quotes from API and set one`() = runTest {
        // Simulamos que no hay citas locales
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

        // Simulamos que el API devuelve citas
        val apiQuotes = listOf(Quote("ApiCita1", "ApiAutor1"), Quote("ApiCita2", "ApiAutor2"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns apiQuotes

        // Simulamos que la inserción en la base de datos no hace nada
        coEvery { quoteRepository.insertQuotes(any()) } returns Unit

        // Llamamos a onCreate()
        quoteViewModel.onCreate()

        // Verificamos que el LiveData tenga una de las citas del API
        assertTrue(quoteViewModel.quoteModel.value in apiQuotes)
    }

    @Test
    fun `toggle favorite updates the LiveData and calls UseCase with correct parameters`() = runTest {
        // Establecemos una cita inicial no favorita
        val initialQuote = Quote("Cita1", "Autor1", false)
        quoteViewModel.quoteModel.value = initialQuote

        // Llamamos a toggleFavorite()
        quoteViewModel.toggleFavorite()

        // Verificamos que el LiveData se actualizó a favorito
        assertEquals(true, quoteViewModel.quoteModel.value?.isFavorite)
        // Verificamos que se llamó al caso de uso con los parámetros correctos
        coVerify { setFavoriteQuoteUseCase(initialQuote, true) }
    }
}
