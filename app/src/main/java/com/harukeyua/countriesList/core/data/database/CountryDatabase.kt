package com.harukeyua.countriesList.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harukeyua.countriesList.core.data.enitity.CountryEntity

@Database(entities = [CountryEntity::class], version = 1, exportSchema = false)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun getCountriesDao(): CountryDao

    companion object {
        const val DB_NAME = "countries.db"
    }
}