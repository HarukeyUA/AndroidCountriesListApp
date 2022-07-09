package com.harukeyua.countriesList.core.domain.datasource

import com.harukeyua.countriesList.core.domain.model.Country

interface CountriesRemoteDataSource {
    suspend fun getCountriesList(): List<Country>
}