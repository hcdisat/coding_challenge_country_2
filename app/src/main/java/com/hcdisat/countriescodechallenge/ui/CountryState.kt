package com.hcdisat.countriescodechallenge.ui

import com.hcdisat.countriescodechallenge.models.Country

sealed class CountryState {
    object LOADING: CountryState()
    class SUCCESS(val countries: List<Country>): CountryState()
    class ERROR(val throwable: Throwable): CountryState()
}
