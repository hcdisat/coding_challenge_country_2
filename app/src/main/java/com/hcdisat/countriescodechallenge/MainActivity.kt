package com.hcdisat.countriescodechallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcdisat.countriescodechallenge.adapters.CountryAdapter
import com.hcdisat.countriescodechallenge.databinding.ActivityMainBinding
import com.hcdisat.countriescodechallenge.ui.CountryState
import com.hcdisat.countriescodechallenge.ui.ErrorDialogFragment
import com.hcdisat.countriescodechallenge.ui.viewmodels.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val countryViewModel: CountriesViewModel by viewModels()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val countryAdapter by lazy {
        CountryAdapter()
    }

    private fun handleState(countryState: CountryState) {
        when(countryState) {
            is CountryState.LOADING -> showHideList(false)

            is CountryState.ERROR -> {
                countryViewModel.errorMessage = countryState.throwable.localizedMessage!!
                ErrorDialogFragment.newErrorDialogBinding()
                    .show(supportFragmentManager, ErrorDialogFragment.TAG)
                showHideList(true)
            }

            is CountryState.SUCCESS -> {
                countryAdapter.setCountries(countryState.countries)
                showHideList(true)
            }
        }
    }

    private fun initCountryList() {
        binding.countryList.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = countryAdapter
        }
    }

    private fun showHideList(shouldShow: Boolean) {
        if (shouldShow) {
            binding.loadingBar.visibility = View.GONE
            binding.countryList.visibility = View.VISIBLE
            return
        }
        binding.loadingBar.visibility = View.VISIBLE
        binding.countryList.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        countryViewModel.countryState.observe(this, ::handleState)
        initCountryList()
    }

    override fun onResume() {
        super.onResume()
        countryViewModel.getCountries()
    }
}