package com.harukeyua.countriesList.di

import android.content.Context
import androidx.room.Room
import com.harukeyua.countriesList.core.data.database.CountryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CountryDatabase::class.java, CountryDatabase.DB_NAME).build()

    @Provides
    @Singleton
    fun provideCountriesDao(db: CountryDatabase) = db.getCountriesDao()
}