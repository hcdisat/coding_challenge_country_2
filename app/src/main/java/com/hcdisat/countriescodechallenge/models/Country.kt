package com.hcdisat.countriescodechallenge.models


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("capital")
    val capital: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("language")
    val name: String,
    @SerializedName("region")
    val region: String
)