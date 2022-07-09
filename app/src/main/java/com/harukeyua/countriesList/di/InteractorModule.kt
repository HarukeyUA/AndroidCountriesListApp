package com.harukeyua.countriesList.di

import com.harukeyua.countriesList.core.domain.interactor.CountriesInteractor
import com.harukeyua.countriesList.core.domain.interactor.CountriesInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class InteractorModule {

    @ViewModelScoped
    @Binds
    abstract fun bindCountriesInteractor(countriesInteractorImpl: CountriesInteractorImpl): CountriesInteractor
}