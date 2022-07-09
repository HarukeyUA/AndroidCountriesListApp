package com.harukeyua.countriesList.core.data.remote

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.harukeyua.countriesList.CountriesQuery
import com.harukeyua.countriesList.core.domain.datasource.CountriesRemoteDataSource
import com.harukeyua.countriesList.core.domain.model.Country
import com.harukeyua.countriesList.core.domain.model.toDomain
import com.harukeyua.countriesList.extensions.throwIfError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApolloClient
) : CountriesRemoteDataSource {

    override suspend fun getCountriesList(): List<Country> {
        val response = apiService.query(CountriesQuery()).execute().throwIfError()
        return response.data?.countries?.map {
            it.toDomain()
        } ?: listOf()
    }
}