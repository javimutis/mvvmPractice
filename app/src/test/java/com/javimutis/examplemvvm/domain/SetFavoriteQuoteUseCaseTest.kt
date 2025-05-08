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

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository  // Mock repositorio

    private lateinit var setFavoriteQuoteUseCase: SetFavoriteQuoteUseCase  // Caso de uso

    @Before
    fun onBefore() {
        // Inicializamos mocks
        MockKAnnotations.init(this)
        setFavoriteQuoteUseCase = SetFavoriteQuoteUseCase(quoteRepository)
    }

    @Test
    fun `when invoke is called then repository updates quote with favorite true`() = runBlocking {
        // Given: Cita no favorita
        val quote = Quote("cita", "autor", false)

        // When: Marcamos como favorita
        setFavoriteQuoteUseCase(quote, true)

        // Then: Verificamos que el repositorio recibió la cita actualizada
        val expectedQuote = quote.copy(isFavorite = true)
        coVerify(exactly = 1) { quoteRepository.updateQuoteFavoriteStatus(expectedQuote) }
    }

    @Test
    fun `when invoke is called then repository updates quote with favorite false`() = runBlocking {
        // Given: Cita favorita
        val quote = Quote("Cita original", "Autor", isFavorite = true)

        // When: Desmarcamos como favorita
        setFavoriteQuoteUseCase(quote, false)

        // Then: Verificamos que el repositorio recibió la cita actualizada
        val expectedQuote = quote.copy(isFavorite = false)
        coVerify(exactly = 1) { quoteRepository.updateQuoteFavoriteStatus(expectedQuote) }
    }
}
