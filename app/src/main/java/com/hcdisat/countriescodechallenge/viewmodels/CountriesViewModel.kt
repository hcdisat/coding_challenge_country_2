package com.hcdisat.countriescodechallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.countriescodechallenge.network.ICountryRepository
import com.hcdisat.countriescodechallenge.ui.CountryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countryRepository: ICountryRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private var _countryState: MutableLiveData<CountryState> =
        MutableLiveData(CountryState.LOADING)
    val countryState: LiveData<CountryState> get() = _countryState

    /**
     * Executes and collects flow of [CountryState]
     */
    fun getCountries() {
        viewModelScope.launch(ioDispatcher) {
            countryRepository.getCountries(this).collect {
                _countryState.postValue(it)
            }
        }
    }
}