package com.hcdisat.countriescodechallenge.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.hcdisat.countriescodechallenge.network.ICountryRepository
import com.hcdisat.countriescodechallenge.ui.CountryState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CountriesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val countryMockRepository = mockk<ICountryRepository>(relaxed = true)

    private lateinit var target: CountriesViewModel

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        target = CountriesViewModel(countryMockRepository, testDispatcher)
    }

    @After
    fun after() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `it should call get countries and return a loading state`() {
        val countryStates = mutableListOf<CountryState>()
        target.countryState.observeForever {
            countryStates.add(it)
        }

        target.getCountries()

        assertThat(countryStates).isNotEmpty()
        assertThat(countryStates).hasSize(2)
        assertThat(countryStates[0]).isInstanceOf(CountryState.LOADING::class.java)
        assertThat(countryStates[0]).isInstanceOf(CountryState.LOADING::class.java)
    }

    @Test
    fun `it should call get countries and return a success state`() {
        every { countryMockRepository.getCountries() } returns flowOf(CountryState.SUCCESS(mockk()))
        val countryStates = mutableListOf<CountryState>()
        target.countryState.observeForever {
            countryStates.add(it)
        }

        target.getCountries()

        assertThat(countryStates).isNotEmpty()
        assertThat(countryStates).hasSize(3)
        assertThat(countryStates[0]).isInstanceOf(CountryState.LOADING::class.java)
        assertThat(countryStates[1]).isInstanceOf(CountryState.LOADING::class.java)
        assertThat(countryStates[2]).isInstanceOf(CountryState.SUCCESS::class.java)
    }

    @Test
    fun `it should call get countries and return an error state`() {
        every { countryMockRepository.getCountries() } returns flowOf(CountryState.ERROR(mockk()))
        val countryStates = mutableListOf<CountryState>()
        target.countryState.observeForever {
            countryStates.add(it)
        }

        target.getCountries()

        assertThat(countryStates).isNotEmpty()
        assertThat(countryStates).hasSize(3)
        assertThat(countryStates[0]).isInstanceOf(CountryState.LOADING::class.java)
        assertThat(countryStates[1]).isInstanceOf(CountryState.LOADING::class.java)
        assertThat(countryStates[2]).isInstanceOf(CountryState.ERROR::class.java)
    }
}