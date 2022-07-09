package com.harukeyua.countriesList.core.domain.datasource

import com.harukeyua.countriesList.core.data.enitity.CountryEntity
import com.harukeyua.countriesList.core.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountriesLocalDataSource {
    val countriesFlow: Flow<List<Country>>

    suspend fun insertCountries(countries: List<Country>)
    fun getCountryInfoFlow(code: String): Flow<Country?>
}