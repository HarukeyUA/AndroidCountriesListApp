package com.harukeyua.countriesList.di

import com.apollographql.apollo3.ApolloClient
import com.harukeyua.countriesList.BuildConfig
import com.harukeyua.countriesList.core.data.remote.CountriesRemoteDataSourceImpl
import com.harukeyua.countriesList.core.domain.datasource.CountriesRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun provideCountriesRemoteDataSource(dataSource: CountriesRemoteDataSourceImpl): CountriesRemoteDataSource
}