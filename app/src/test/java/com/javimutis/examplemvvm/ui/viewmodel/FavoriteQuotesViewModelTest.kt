package com.javimutis.examplemvvm.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.javimutis.examplemvvm.domain.GetFavoriteQuoteUseCase
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteQuotesViewModelTest {

    @RelaxedMockK
    private lateinit var getFavoriteQuoteUseCase: GetFavoriteQuoteUseCase

    private lateinit var favoriteQuotesViewModel: FavoriteQuotesViewModel

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        // Configure el mock del caso de uso para devolver un flujo vacío al inicio
        coEvery { getFavoriteQuoteUseCase() } returns flowOf(emptyList())
        favoriteQuotesViewModel = FavoriteQuotesViewModel(getFavoriteQuoteUseCase)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is created, it fetches favorite quotes`() = runTest {
        // Given: Una lista simulada de citas favoritas
        val favoriteQuotes = listOf(
            Quote("Cita favorita 1", "Autor 1", true),
            Quote("Cita favorita 2", "Autor 2", true)
        )
        // Cambiar el flujo que devuelve `getFavoriteQuoteUseCase`
        coEvery { getFavoriteQuoteUseCase() } returns flowOf(favoriteQuotes)

        // When: Refrescamos las citas favoritas
        favoriteQuotesViewModel.refreshFavorites()

        // Esperar un tiempo para permitir que las corutinas actualicen el estado
        advanceUntilIdle()

        // Then: Verificamos que el StateFlow contiene las citas simuladas
        assertEquals(favoriteQuotes, favoriteQuotesViewModel.favoriteQuotes.value)
    }

    @Test
    fun `when refreshFavorites is called, it updates the favoriteQuotes state flow`() = runTest {
        // Given: Inicialmente no hay citas favoritas
        coEvery { getFavoriteQuoteUseCase() } returns flowOf(emptyList())

        // When: Llamamos a refreshFavorites
        favoriteQuotesViewModel.refreshFavorites()

        // Esperar un tiempo para permitir que las corutinas actualicen el estado
        advanceUntilIdle()

        // Then: El flujo inicial es vacío
        assertEquals(emptyList<Quote>(), favoriteQuotesViewModel.favoriteQuotes.value)

        // Cambiamos el caso de uso para devolver nuevas citas
        val newFavoriteQuotes = listOf(
            Quote("Nueva cita favorita 1", "Autor A", true)
        )
        coEvery { getFavoriteQuoteUseCase() } returns flowOf(newFavoriteQuotes)

        // Refrescamos las citas favoritas nuevamente
        favoriteQuotesViewModel.refreshFavorites()

        // Esperar un tiempo para permitir que las corutinas actualicen el estado
        advanceUntilIdle()

        // El flujo ahora debería contener las nuevas citas
        assertEquals(newFavoriteQuotes, favoriteQuotesViewModel.favoriteQuotes.value)
    }
}