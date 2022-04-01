package com.hcdisat.countriescodechallenge.network

import com.hcdisat.countriescodechallenge.models.Country
import retrofit2.Response
import retrofit2.http.GET

/**
 * [ICountryService] used to set interact with retrofit and do http requests
 */
interface ICountryService {

    /**
     * gets all countries
     * @return response with a list of countries
     */
    @GET(COUNTRY_PATH)
    suspend fun getCountries(): Response<List<Country>>

    companion object {
        const val BASE_PATH = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/"
        private const val COUNTRY_PATH = "countries.json"
    }
}