package com.harukeyua.countriesList.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harukeyua.countriesList.core.data.enitity.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CountryEntity>)

    @Query("SELECT * FROM CountryEntity")
    fun getAll(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM CountryEntity WHERE code = :countryCode")
    fun getByCode(countryCode: String): Flow<CountryEntity?>
}