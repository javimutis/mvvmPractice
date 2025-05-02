package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRandomQuoteUseCaseTest {

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository
    lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this) // Prepara los mocks
        getRandomQuoteUseCase = GetRandomQuoteUseCase(quoteRepository)
    }

    @Test
    fun `when the database is empty then return null`() = runBlocking {
        // Given - Simula que la base local no tiene frases
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

        // When - Ejecuta el caso de uso
        val response = getRandomQuoteUseCase()

        // Then - Verifica que el resultado sea null
        assert(response == null)
    }

    @Test
    fun `when the database is not empty then return a quote`() = runBlocking {
        // Given - Simula que la base local tiene una frase
        val quoteList = listOf(Quote("cita", "autor", false))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList

        // When - Ejecuta el caso de uso
        val response = getRandomQuoteUseCase()

        // Then - Verifica que se devuelva una frase (en este caso la única disponible)
        assert(response == quoteList.first())
    }
}
