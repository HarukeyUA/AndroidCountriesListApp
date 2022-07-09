package com.harukeyua.countriesList.di

import android.content.Context
import androidx.room.Room
import com.harukeyua.countriesList.core.data.database.CountriesLocalDataSourceImpl
import com.harukeyua.countriesList.core.data.database.CountryDatabase
import com.harukeyua.countriesList.core.data.database.CountryDatabase.Companion.DB_NAME
import com.harukeyua.countriesList.core.domain.datasource.CountriesLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(localDataSourceImpl: CountriesLocalDataSourceImpl): CountriesLocalDataSource
}