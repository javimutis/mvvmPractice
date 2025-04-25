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
        MockKAnnotations.init(this)
        getRandomQuoteUseCase = GetRandomQuoteUseCase(quoteRepository)
    }

    @Test
    fun `when the database is empty then return null`() = runBlocking {

        //Given
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()
        //When
        val response = getRandomQuoteUseCase()
        //Then
        assert(response == null)
    }

    @Test
    fun `when the database is not empty then return a quote`() = runBlocking {
        //Given
        val quoteList = listOf(Quote("cita", "autor"))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList
        //When
        val response = getRandomQuoteUseCase()
        //Then
        assert(response == quoteList.first())
    }
}
