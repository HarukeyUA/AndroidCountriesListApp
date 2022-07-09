package com.harukeyua.countriesList.features.countriesList.model

import com.harukeyua.countriesList.R
import com.harukeyua.countriesList.core.domain.model.Country
import com.harukeyua.countriesList.utils.ItemModel

data class CountryItemModel(
    val code: String,
    val name: String,
    val continent: String,
    val capital: String,
    val phone: String,
    val currency: String,
    val language: String,
    val selected: Boolean = false
) : ItemModel {
    val flagResource: Int?
        get() {
            return when (code) {
                "TN" -> R.drawable.tunisia_flag
                "TM" -> R.drawable.turkmenistan_flag
                "FJ" -> R.drawable.fiji_flag
                "TV" -> R.drawable.tuvalu_flag
                "AE" -> R.drawable.uae_flag
                "UG" -> R.drawable.uganda_flag
                "UA" -> R.drawable.ukraine_flag
                "UY" -> R.drawable.uruguay_flag
                "GB" -> R.drawable.uk_flag
                "UZ" -> R.drawable.uzbekistan_flag
                "VU" -> R.drawable.vanuatu_flag
                "VA" -> R.drawable.vatican_flag
                "VE" -> R.drawable.venezuela_flag
                "VN" -> R.drawable.vietnam_flag
                else -> null
            }
        }
}

fun Country.toItemModel(): CountryItemModel {
    return CountryItemModel(
        code = code,
        name = name,
        continent = continent,
        capital = capital,
        phone = phone,
        currency = currency,
        language = language
    )
}
