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
    private lateinit var quoteRepository: QuoteRepository  // Simulamos el repositorio (no es el real)

    private lateinit var getFavoriteQuoteUseCase: GetFavoriteQuoteUseCase  // Caso de uso a testear

    @Before
    fun onBefore() {
        // Inicializamos los mocks antes de cada prueba
        MockKAnnotations.init(this)
        // Creamos la instancia del use case pasando el repositorio simulado
        getFavoriteQuoteUseCase = GetFavoriteQuoteUseCase(quoteRepository)
    }

    @Test
    fun `invoke should return non-empty list when repository has favorite quotes`() = runTest {
        // Given: El repositorio devuelve una lista de citas favoritas
        val favoriteQuotes = listOf(
            Quote("Cita favorita 1", "Autor 1", true),
            Quote("Cita favorita 2", "Autor 2", true)
        )
        coEvery { quoteRepository.getFavoriteQuotes() } returns flowOf(favoriteQuotes)

        // When: Ejecutamos el use case y recolectamos el resultado del Flow
        val resultQuotes = mutableListOf<Quote>()
        val resultFlow = getFavoriteQuoteUseCase.invoke()
        resultFlow.collect { resultQuotes.addAll(it) }  // Guardamos lo que sale del Flow

        // Then: Comprobamos que la lista obtenida sea igual a la que simulamos
        assertEquals(favoriteQuotes, resultQuotes)
    }

    @Test
    fun `invoke should return empty list when repository has no favorite quotes`() = runTest {
        // Given: El repositorio devuelve lista vacía
        coEvery { quoteRepository.getFavoriteQuotes() } returns flowOf(emptyList())

        // When: Ejecutamos el use case y recolectamos el resultado
        val resultQuotes = mutableListOf<Quote>()
        val resultFlow = getFavoriteQuoteUseCase.invoke()
        resultFlow.collect { resultQuotes.addAll(it) }

        // Then: Comprobamos que la lista obtenida esté vacía
        assertEquals(emptyList<Quote>(), resultQuotes)
    }
}
