package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

//Aquí probamos que el caso de uso que obtiene una cita aleatoria se comporte bien, devolviendo null si no hay citas, o devolviendo una válida si existen.
class GetRandomQuoteUseCaseTest {

    @RelaxedMockK private lateinit var quoteRepository: QuoteRepository
    lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this) // Inicializamos los mocks
        getRandomQuoteUseCase = GetRandomQuoteUseCase(quoteRepository) // Inyectamos el repositorio simulado
    }

    @Test
    fun `when the database is empty then return null`() = runBlocking {
        // Given: Simulamos que la base local no tiene citas
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

        // When: Ejecutamos el caso de uso
        val response = getRandomQuoteUseCase()

        // Then: Esperamos que el resultado sea null
        assert(response == null)
    }

    @Test
    fun `when the database is not empty then return a quote`() = runBlocking {
        // Given: Simulamos que la base local tiene una cita
        val quoteList = listOf(Quote("cita", "autor", false))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList

        // When: Ejecutamos el caso de uso
        val response = getRandomQuoteUseCase()

        // Then: Esperamos que devuelva una cita (en este caso, la única disponible)
        assert(response == quoteList.first())
    }
}
