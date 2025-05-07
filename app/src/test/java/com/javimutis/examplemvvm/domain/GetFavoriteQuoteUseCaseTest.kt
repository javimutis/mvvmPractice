package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFavoriteQuoteUseCaseTest {

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    private lateinit var getFavoriteQuoteUseCase: GetFavoriteQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getFavoriteQuoteUseCase = GetFavoriteQuoteUseCase(quoteRepository)
    }

    @Test
    fun `invoke should return non-empty list when repository has favorite quotes`() = runTest {
        // Given: Simulamos que el repositorio tiene algunas citas favoritas
        val favoriteQuotes = listOf(
            Quote("Cita favorita 1", "Autor 1", true),
            Quote("Cita favorita 2", "Autor 2", true)
        )
        coEvery { quoteRepository.getFavoriteQuotes() } returns flowOf(favoriteQuotes)

        // When: Ejecutamos el use case y recolectamos el resultado
        val resultQuotes = mutableListOf<Quote>()
        val resultFlow = getFavoriteQuoteUseCase.invoke()
        resultFlow.collect { resultQuotes.addAll(it) } // Recolectamos los elementos del Flow

        // Then: Verificamos que el flujo contiene las citas simuladas
        assertEquals(favoriteQuotes, resultQuotes)
    }

    @Test
    fun `invoke should return empty list when repository has no favorite quotes`() = runTest {
        // Given: Simulamos que el repositorio no tiene citas favoritas
        coEvery { quoteRepository.getFavoriteQuotes() } returns flowOf(emptyList())

        // When: Ejecutamos el use case y recolectamos el resultado
        val resultQuotes = mutableListOf<Quote>()
        val resultFlow = getFavoriteQuoteUseCase.invoke()
        resultFlow.collect { resultQuotes.addAll(it) } // Recolectamos los elementos del Flow

        // Then: Verificamos que el flujo está vacío
        assertEquals(emptyList<Quote>(), resultQuotes)
    }
}