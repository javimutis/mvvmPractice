package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetQuotesUseCaseTest {

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository  // Simulamos el repositorio

    lateinit var getQuotesUseCase: GetQuotesUseCase  // Caso de uso a testear

    @Before
    fun onBefore() {
        // Inicializamos los mocks antes de cada prueba
        MockKAnnotations.init(this)
        getQuotesUseCase = GetQuotesUseCase(quoteRepository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {
        // Given: La API devuelve lista vacía
        coEvery { quoteRepository.getAllQuotesFromApi() } returns emptyList()

        // When: Ejecutamos el use case
        getQuotesUseCase()

        // Then: Comprobamos el comportamiento esperado
        coVerify(exactly = 0){ quoteRepository.clearQuotes() }  // No limpia base local
        coVerify(exactly = 0){ quoteRepository.insertQuotes(any()) }  // No inserta nada
        coVerify(exactly = 1){ quoteRepository.getAllQuotesFromDatabase() }  // Usa base local
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        val myList = listOf(Quote("cita","autor"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns myList

        val response = getQuotesUseCase()

        coVerify(exactly = 1){ quoteRepository.clearQuotes() }
        coVerify(exactly = 1){ quoteRepository.insertQuotes(any()) }
        coVerify(exactly = 0){ quoteRepository.getAllQuotesFromDatabase() }
        assertEquals(myList, response)
    }
}

