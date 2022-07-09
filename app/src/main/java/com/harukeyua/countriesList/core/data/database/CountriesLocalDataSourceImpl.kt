package com.harukeyua.countriesList.core.data.database

import com.harukeyua.countriesList.core.data.enitity.toEntity
import com.harukeyua.countriesList.core.domain.datasource.CountriesLocalDataSource
import com.harukeyua.countriesList.core.domain.model.Country
import com.harukeyua.countriesList.core.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesLocalDataSourceImpl @Inject constructor(
    private val countriesDao: CountryDao
) : CountriesLocalDataSource {
    override val countriesFlow =
        countriesDao.getAll().map { countries -> countries.map { it.toDomain() } }

    override suspend fun insertCountries(countries: List<Country>) {
        countriesDao.insertAll(countries.map { it.toEntity() })
    }

    override fun getCountryInfoFlow(code: String): Flow<Country?> {
        return countriesDao.getByCode(code).map { it?.toDomain() }
    }
}