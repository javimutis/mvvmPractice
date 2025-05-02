package com.javimutis.examplemvvm.domain

import com.javimutis.examplemvvm.data.QuoteRepository
import com.javimutis.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SetFavoriteQuoteUseCaseTest {

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository
    private lateinit var setFavoriteQuoteUseCase: SetFavoriteQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this) // Prepara los mocks
        setFavoriteQuoteUseCase = SetFavoriteQuoteUseCase(quoteRepository)
    }

    @Test
    fun `when invoke is called then repository updates quote with favorite true`() = runBlocking {
        // Given: Una quote original
        val quote = Quote("cita", "autor", false)
        //When - ejecutamos el caso de uso para marcar como favorito
        setFavoriteQuoteUseCase(quote, true)
        // Then - verificamos que el repositorio recibe la quote actualizada
        val expectedQuote = quote.copy(isFavorite = true)
        coVerify(exactly = 1) { quoteRepository.updateQuoteFavoriteStatus(expectedQuote) }

    }

    @Test
    fun `when invoke is called then repository updates quote with favorite false`() = runBlocking {
        // Given - una quote original marcada como favorita
        val quote = Quote("Cita original", "Autor", isFavorite = true)
        //When - ejecutamos el caso de uso para desmarcar como favorito
        setFavoriteQuoteUseCase(quote, false)
        // Then - verificamos que el repositorio recibe la quote actualizada
        val expectedQuote = quote.copy(isFavorite = false)
        coVerify(exactly = 1) { quoteRepository.updateQuoteFavoriteStatus(expectedQuote) }
    }
}