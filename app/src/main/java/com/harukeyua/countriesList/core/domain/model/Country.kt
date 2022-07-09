package com.harukeyua.countriesList.core.domain.model

import com.harukeyua.countriesList.CountriesQuery
import com.harukeyua.countriesList.core.data.enitity.CountryEntity

data class Country(
    val code: String,
    val continent: String,
    val name: String,
    val capital: String,
    val phone: String,
    val currency: String,
    val language: String,
    val emojiCode: String
)

fun CountriesQuery.Country.toDomain(): Country {
    return Country(
        code = code,
        continent = continent.name,
        name = name,
        capital = capital ?: "",
        phone = phone,
        currency = currency ?: "",
        language = languages.firstOrNull()?.name ?: "",
        emojiCode = emoji
    )
}

fun CountryEntity.toDomain(): Country {
    return Country(
        code = code,
        name = name,
        continent = continent,
        capital = capital,
        phone = phone,
        currency = currency,
        language = language,
        emojiCode = emojiCode
    )
}
