package com.hcdisat.countriescodechallenge.network

import com.hcdisat.countriescodechallenge.ui.CountryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

interface ICountryRepository {
    val countryFlow: StateFlow<CountryState>
    fun getCountries(): Flow<CountryState>
}

/**
 * Repository layer used to do http requests
 */
class CountryRepository @Inject constructor(
    private val countryService: ICountryService
) : ICountryRepository {

    private var _countryFlow: MutableStateFlow<CountryState> =
        MutableStateFlow(CountryState.LOADING)
    override val countryFlow: StateFlow<CountryState> get() = _countryFlow

    /**
     * emits [Flow] of [CountryState]
     */
    override fun getCountries(): Flow<CountryState> = flow {
        try {
            val response = countryService.getCountries()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(CountryState.SUCCESS(it))
                } ?: throw Exception()
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            emit(CountryState.ERROR(e))
        }
    }

}