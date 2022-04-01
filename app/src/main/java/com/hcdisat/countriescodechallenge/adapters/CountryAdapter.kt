package com.hcdisat.countriescodechallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.countriescodechallenge.databinding.CountryItemBinding
import com.hcdisat.countriescodechallenge.models.Country
import com.hcdisat.countriescodechallenge.models.nameWithRegion

class CountryAdapter(
    private var countries: List<Country> = listOf()
) : RecyclerView.Adapter<CountryViewHolder>() {

    fun setCountries(newCounties: List<Country>) {
        countries = newCounties
        notifyItemRangeChanged(0, newCounties.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            CountryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) =
        holder.bind(countries[position])

    override fun getItemCount(): Int = countries.size
}

class CountryViewHolder(
    private val binding: CountryItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        binding.nameRegionText.text = country.nameWithRegion()
        binding.codeText.text = country.code
        binding.capitalText.text = country.capital
    }
}