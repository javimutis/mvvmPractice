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

//Aquí probamos que el ViewModel se comporte correctamente al inicializarse, al pedir citas aleatorias, y al manejar escenarios con o sin datos locales.
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
        MockKAnnotations.init(this)
        quoteViewModel = QuoteViewModel(
            getQuotesUseCase, getRandomQuoteUseCase, setFavoriteQuoteUseCase, quoteRepository
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is created at the first time, get a local or API quote`() = runTest {
        val quoteList = listOf(Quote("Cita1", "Autor1"), Quote("Cita2", "Autor2"))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList

        quoteViewModel.onCreate()

        assertTrue(quoteViewModel.quoteModel.value in quoteList)
    }

    @Test
    fun `when randomQuoteUseCase returns a quote, set on the LiveData`() = runTest {
        val quote = Quote("Cita1", "Autor1")
        coEvery { getRandomQuoteUseCase() } returns quote

        quoteViewModel.randomQuote()

        assertEquals(quote, quoteViewModel.quoteModel.value)
    }

    @Test
    fun `if randomQuoteUseCase returns null, keep the last value`() = runTest {
        val quote = Quote("Cita1", "Autor1")
        quoteViewModel.quoteModel.value = quote

        coEvery { getRandomQuoteUseCase() } returns null

        quoteViewModel.randomQuote()

        assertEquals(quote, quoteViewModel.quoteModel.value)
    }

    @Test
    fun `when no local quotes, get all quotes from API and set one`() = runTest {
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

        val apiQuotes = listOf(Quote("ApiCita1", "ApiAutor1"), Quote("ApiCita2", "ApiAutor2"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns apiQuotes

        coEvery { quoteRepository.insertQuotes(any()) } returns Unit

        quoteViewModel.onCreate()

        assertTrue(quoteViewModel.quoteModel.value in apiQuotes)
    }

    @Test
    fun `toggle favorite updates the LiveData and calls UseCase with correct parameters`() = runTest {
        val initialQuote = Quote("Cita1", "Autor1", false)
        quoteViewModel.quoteModel.value = initialQuote

        quoteViewModel.toggleFavorite()

        assertEquals(true, quoteViewModel.quoteModel.value?.isFavorite)
        coVerify { setFavoriteQuoteUseCase(initialQuote, true) }
    }
}