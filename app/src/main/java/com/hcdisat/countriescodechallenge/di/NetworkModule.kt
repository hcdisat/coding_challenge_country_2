package com.hcdisat.countriescodechallenge.di

import com.hcdisat.countriescodechallenge.network.CountryRepository
import com.hcdisat.countriescodechallenge.network.ICountryRepository
import com.hcdisat.countriescodechallenge.network.ICountryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): ICountryService =
        Retrofit.Builder()
            .baseUrl(ICountryService.BASE_PATH)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ICountryService::class.java)

    @Provides
    fun providesCountryRepository(
        countryService: ICountryService
    ): ICountryRepository = CountryRepository(countryService)
}

private const val REQUEST_TIMEOUT = 20L