package com.harukeyua.countriesList.features.countriesList

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harukeyua.countriesList.core.domain.interactor.CountriesInteractor
import com.harukeyua.countriesList.core.domain.model.Country
import com.harukeyua.countriesList.features.countriesList.model.toItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countriesInteractor: CountriesInteractor
) : ViewModel() {

    private val selectedCountryKeyFlow = savedStateHandle.getStateFlow(SELECTED_COUNTRY_KEY, "")
    private val searchQueryFlow = savedStateHandle.getStateFlow(SEARCH_QUERY_KEY, "")

    val countriesListFlow =
        countriesInteractor.countriesFlow
            .combine(searchQueryFlow.debounce(300)) { countries: List<Country>, query: String ->
                countries.filter { it.name.contains(query) }
            }
            .map { countries -> countries.map { it.toItemModel() } }
            .combine(selectedCountryKeyFlow) { countries, selectedKey ->
                countries.map { country ->
                    if (country.code == selectedKey) {
                        country.copy(selected = true)
                    } else {
                        country
                    }
                }
            }
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                listOf()
            )

    val selectedCountryInfoFlow = selectedCountryKeyFlow
        .filter { it.isNotEmpty() }
        .flatMapLatest {
            countriesInteractor.getCountryFlowByCode(it)
        }
        .mapNotNull {
            it?.toItemModel()
        }

    private val errorChannel = Channel<String>(Channel.UNLIMITED)
    val errorEventFlow = errorChannel.receiveAsFlow()

    private val defaultExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.message?.also { errorChannel.trySend(it) }
        }

    init {
        viewModelScope.launch(defaultExceptionHandler) {
            countriesInteractor.fetchCountries()
        }
    }

    fun selectCountry(code: String) {
        if (code.isNotEmpty()) {
            savedStateHandle[SELECTED_COUNTRY_KEY] = code
        }
    }

    fun performSearch(input: String) {
        savedStateHandle[SEARCH_QUERY_KEY] = input.trim()
    }

    companion object {
        private const val SELECTED_COUNTRY_KEY = "SELECTED_COUNTRY"
        private const val SEARCH_QUERY_KEY = "SEARCH_QUERY"
    }
}