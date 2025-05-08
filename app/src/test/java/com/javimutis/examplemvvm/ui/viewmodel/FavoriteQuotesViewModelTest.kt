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
    private lateinit var getFavoriteQuoteUseCase: GetFavoriteQuoteUseCase  // Mock del caso de uso

    private lateinit var favoriteQuotesViewModel: FavoriteQuotesViewModel  // ViewModel a testear

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()  // Permite testear LiveData

    private val testDispatcher = UnconfinedTestDispatcher()  // Dispatcher de prueba

    @Before
    fun onBefore() {
        // Inicializamos mocks y configuramos el main dispatcher de test
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        // Simulamos que el use case inicialmente devuelve lista vacía
        coEvery { getFavoriteQuoteUseCase() } returns flowOf(emptyList())
        // Creamos el ViewModel con el mock
        favoriteQuotesViewModel = FavoriteQuotesViewModel(getFavoriteQuoteUseCase)
    }

    @After
    fun onAfter() {
        // Reseteamos el dispatcher principal
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is created, it fetches favorite quotes`() = runTest {
        // Given: Lista simulada de favoritas
        val favoriteQuotes = listOf(
            Quote("Cita favorita 1", "Autor 1", true),
            Quote("Cita favorita 2", "Autor 2", true)
        )
        // Cambiamos el use case para que devuelva estas citas
        coEvery { getFavoriteQuoteUseCase() } returns flowOf(favoriteQuotes)

        // When: Refrescamos favoritas
        favoriteQuotesViewModel.refreshFavorites()
        advanceUntilIdle()  // Esperamos que las corutinas terminen

        // Then: El ViewModel tiene las citas simuladas en su StateFlow
        assertEquals(favoriteQuotes, favoriteQuotesViewModel.favoriteQuotes.value)
    }

    @Test
    fun `when refreshFavorites is called, it updates the favoriteQuotes state flow`() = runTest {
        // Given: Use case devuelve lista vacía
        coEvery { getFavoriteQuoteUseCase() } returns flowOf(emptyList())

        // When: Llamamos a refreshFavorites
        favoriteQuotesViewModel.refreshFavorites()
        advanceUntilIdle()

        // Then: El flujo inicial es vacío
        assertEquals(emptyList<Quote>(), favoriteQuotesViewModel.favoriteQuotes.value)

        // Given: Cambiamos el use case para devolver nuevas favoritas
        val newFavoriteQuotes = listOf(
            Quote("Nueva cita favorita 1", "Autor A", true)
        )
        coEvery { getFavoriteQuoteUseCase() } returns flowOf(newFavoriteQuotes)

        // When: Refrescamos nuevamente
        favoriteQuotesViewModel.refreshFavorites()
        advanceUntilIdle()

        // Then: El flujo ahora contiene las nuevas citas
        assertEquals(newFavoriteQuotes, favoriteQuotesViewModel.favoriteQuotes.value)
    }
}
