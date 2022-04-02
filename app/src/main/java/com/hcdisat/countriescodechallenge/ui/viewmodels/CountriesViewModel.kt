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
import kotlinx.coroutines.flow.collect
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

    private var _errorMessage: String = ""
    var errorMessage: String
    get() = _errorMessage
    set(value) { _errorMessage = value }

    /**
     * Executes and collects flow of [CountryState]
     */
    fun getCountries() {
        _countryState.value = CountryState.LOADING
        viewModelScope.launch(ioDispatcher) {
            countryRepository.getCountries().collect {
                _countryState.postValue(it)
            }
        }
    }
}