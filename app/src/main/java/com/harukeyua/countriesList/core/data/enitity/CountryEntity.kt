package com.harukeyua.countriesList.core.data.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.harukeyua.countriesList.core.domain.model.Country

@Entity
data class CountryEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val continent: String,
    val capital: String,
    val phone: String,
    val currency: String,
    val language: String
)

fun Country.toEntity(): CountryEntity {
    return CountryEntity(
        code = code,
        name = name,
        continent = continent,
        capital = capital,
        phone = phone,
        currency = currency,
        language = language
    )
}
