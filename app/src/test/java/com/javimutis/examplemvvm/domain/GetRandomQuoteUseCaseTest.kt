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
    private lateinit var quoteRepository: QuoteRepository  // Repositorio simulado

    lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase  // Caso de uso

    @Before
    fun onBefore() {
        // Inicializamos mocks
        MockKAnnotations.init(this)
        // Inyectamos el mock al caso de uso
        getRandomQuoteUseCase = GetRandomQuoteUseCase(quoteRepository)
    }

    @Test
    fun `when the database is empty then return null`() = runBlocking {
        // Given: Base local vacía
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

        // When: Ejecutamos el caso de uso
        val response = getRandomQuoteUseCase()

        // Then: El resultado debe ser null
        assert(response == null)
    }

    @Test
    fun `when the database is not empty then return a quote`() = runBlocking {
        // Given: Base local con una cita
        val quoteList = listOf(Quote("cita", "autor", false))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList

        // When: Ejecutamos el caso de uso
        val response = getRandomQuoteUseCase()

        // Then: El resultado es la cita disponible
        assert(response == quoteList.first())
    }
}

