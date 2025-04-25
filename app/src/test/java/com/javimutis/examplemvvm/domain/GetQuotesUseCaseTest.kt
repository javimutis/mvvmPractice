package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetQuotesUseCaseTest {

    // Crea un mock relajado (no lanza error si no se usa un método del mock)
    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    // Instancia del use case a testear
    lateinit var getQuotesUseCase: GetQuotesUseCase

    @Before
    fun onBefore() {
        // Inicializa los mocks antes de cada test
        MockKAnnotations.init(this)
        getQuotesUseCase = GetQuotesUseCase(quoteRepository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {
        // Given - Simula que la API devuelve una lista vacía
        coEvery { quoteRepository.getAllQuotesFromApi() } returns emptyList()

        // When - Ejecuta el use case
        getQuotesUseCase()

        // Then - Verifica que:
        coVerify(exactly = 0){ quoteRepository.clearQuotes() } // No se limpió la base
        coVerify(exactly = 0){ quoteRepository.insertQuotes(any()) } // No se insertó nada
        coVerify(exactly = 1) {  quoteRepository.getAllQuotesFromDatabase() } // Se usó la base local
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        // Given - Simula que la API devuelve una frase
        val myList = listOf(Quote("cita","autor"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns myList

        // When - Ejecuta el use case
        val response = getQuotesUseCase()

        // Then - Verifica que:
        coVerify(exactly = 1){ quoteRepository.clearQuotes() } // Se limpió la base local
        coVerify(exactly = 1){ quoteRepository.insertQuotes(any()) } // Se insertaron nuevas frases
        coVerify(exactly = 0){ quoteRepository.getAllQuotesFromDatabase() } // No se usó la base local
        assert(myList == response) // El resultado fue lo mismo que devolvió la API
    }
}
