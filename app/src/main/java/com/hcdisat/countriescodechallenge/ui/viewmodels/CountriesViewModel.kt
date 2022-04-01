package com.hcdisat.countriescodechallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.countriescodechallenge.di.UtilitiesModule.IODispatcher
import com.hcdisat.countriescodechallenge.network.ICountryRepository
import com.hcdisat.countriescodechallenge.ui.CountryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countryRepository: ICountryRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private var _countryState: MutableLiveData<CountryState> =
        MutableLiveData(CountryState.LOADING)
    val countryState: LiveData<CountryState> get() = _countryState

    init {
        viewModelScope.launch {
           countryRepository.countryFlow.collect {
               _countryState.postValue(it)
           }
        }
    }

    /**
     * Executes and collects flow of [CountryState]
     */
    fun getCountries() {
        viewModelScope.launch(ioDispatcher) {
            countryRepository.getCountries(this)
        }
    }
}