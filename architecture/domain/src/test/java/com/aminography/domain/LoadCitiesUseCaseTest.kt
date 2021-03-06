package com.aminography.domain

import com.aminography.domain.base.Result
import com.aminography.domain.city.CityRepository
import com.aminography.domain.city.LoadCitiesUseCase
import com.aminography.test.CoroutineTest
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * @author aminography
 */
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoadCitiesUseCaseTest : CoroutineTest() {

    private val cityRepository: CityRepository = mockk()

    @Test
    fun `load cities`() = runBlockingTest {
        // Given
        coEvery { cityRepository.loadCities() } just Runs

        val useCase = LoadCitiesUseCase(cityRepository, testDispatcher)

        // When
        val result = useCase(Unit).toList()

        // Then
        assertSame(result[0], Result.Loading)
        assertEquals(result[1], Result.Success(Unit))

        coVerifySequence {
            cityRepository.loadCities()
        }
    }
}