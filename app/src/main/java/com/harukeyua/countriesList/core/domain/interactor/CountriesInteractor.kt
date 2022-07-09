package com.harukeyua.countriesList.core.domain.interactor

import com.harukeyua.countriesList.core.data.enitity.CountryEntity
import com.harukeyua.countriesList.core.domain.datasource.CountriesRemoteDataSource
import com.harukeyua.countriesList.core.domain.datasource.CountriesLocalDataSource
import com.harukeyua.countriesList.core.domain.model.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CountriesInteractor {
    val countriesFlow: Flow<List<Country>>

    suspend fun fetchCountries()
    fun getCountryFlowByCode(code: String): Flow<Country?>
}

class CountriesInteractorImpl @Inject constructor(
    private val remoteDataSource: CountriesRemoteDataSource,
    private val localDataSource: CountriesLocalDataSource
) : CountriesInteractor {

    override val countriesFlow = localDataSource.countriesFlow

    override suspend fun fetchCountries() {
        val countries = remoteDataSource.getCountriesList()
        localDataSource.insertCountries(countries)
    }

    override fun getCountryFlowByCode(code: String): Flow<Country?> {
        return localDataSource.getCountryInfoFlow(code)
    }
}