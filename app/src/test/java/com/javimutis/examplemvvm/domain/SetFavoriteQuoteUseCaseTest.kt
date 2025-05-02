package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

//Aquí probamos que, cuando usamos el caso de uso para cambiar el estado favorito de una quote, el repositorio efectivamente recibe la versión actualizada.
// Clase de test para SetFavoriteQuoteUseCase (caso de uso que marca/desmarca como favorito)
class SetFavoriteQuoteUseCaseTest {

    // Creamos un mock del repositorio (no es real, simula comportamiento)
    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    // Instancia del caso de uso que queremos probar
    private lateinit var setFavoriteQuoteUseCase: SetFavoriteQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this) // Inicializa los mocks antes de cada test
        setFavoriteQuoteUseCase = SetFavoriteQuoteUseCase(quoteRepository) // Inyectamos el mock al caso de uso
    }

    @Test
    fun `when invoke is called then repository updates quote with favorite true`() = runBlocking {
        // Given: Tenemos una quote (cita) no favorita
        val quote = Quote("cita", "autor", false)

        // When: Ejecutamos el caso de uso para marcarla como favorita
        setFavoriteQuoteUseCase(quote, true)

        // Then: Verificamos que el repositorio recibió la quote actualizada correctamente (favorito = true)
        val expectedQuote = quote.copy(isFavorite = true) // Creamos la versión esperada
        coVerify(exactly = 1) { quoteRepository.updateQuoteFavoriteStatus(expectedQuote) } // Verificamos que se llamó una vez
    }

    @Test
    fun `when invoke is called then repository updates quote with favorite false`() = runBlocking {
        // Given: Tenemos una quote ya marcada como favorita
        val quote = Quote("Cita original", "Autor", isFavorite = true)

        // When: Ejecutamos el caso de uso para desmarcarla
        setFavoriteQuoteUseCase(quote, false)

        // Then: Verificamos que el repositorio recibió la quote actualizada correctamente (favorito = false)
        val expectedQuote = quote.copy(isFavorite = false)
        coVerify(exactly = 1) { quoteRepository.updateQuoteFavoriteStatus(expectedQuote) }
    }
}