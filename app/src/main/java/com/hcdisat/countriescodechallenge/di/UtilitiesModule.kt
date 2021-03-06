package com.hcdisat.countriescodechallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@InstallIn(ViewModelComponent::class)
@Module
class UtilitiesModule {

    @Qualifier
    annotation class IODispatcher

    @IODispatcher
    @ViewModelScoped
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}