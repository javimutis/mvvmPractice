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
        // Given: La API devuelve una lista con una cita
        val myList = listOf(Quote("cita","autor"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns myList

        // When: Ejecutamos el use case
        val response = getQuotesUseCase()

        // Then: Comprobamos que:
        coVerify(exactly = 1){ quoteRepository.clearQuotes() }  // Limpia base local
        coVerify(exactly = 1){ quoteRepository.insertQuotes(any()) }  // Inserta nuevas frases
        coVerify(exactly = 0){ quoteRepository.getAllQuotesFromDatabase() }  // No usa base local
        assert(myList == response)  // El resultado es lo que devuelve la API
    }
}

