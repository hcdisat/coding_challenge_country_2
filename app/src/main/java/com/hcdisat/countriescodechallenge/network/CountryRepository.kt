package com.hcdisat.countriescodechallenge.network

import com.hcdisat.countriescodechallenge.ui.CountryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

interface ICountryRepository {
    suspend fun getCountries(coroutineScope: CoroutineScope): Flow<CountryState>
}

/**
 * Repository layer used to do http requests
 */
class CountryRepository @Inject constructor(
    private val countryService: ICountryService
    ) : ICountryRepository {

    /**
     * emits [Flow] of [CountryState]
     */
    override suspend fun getCountries(
        coroutineScope: CoroutineScope
    ) = flow {
        coroutineScope.launch {
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

}