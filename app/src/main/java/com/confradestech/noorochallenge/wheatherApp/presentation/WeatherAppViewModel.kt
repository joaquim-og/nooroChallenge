package com.confradestech.noorochallenge.wheatherApp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confradestech.noorochallenge.core.data.local.PreferencesManager
import com.confradestech.noorochallenge.core.domain.util.NetworkError
import com.confradestech.noorochallenge.core.domain.util.onError
import com.confradestech.noorochallenge.core.domain.util.onSuccess
import com.confradestech.noorochallenge.wheatherApp.domain.GetWeatherInfoUseCase
import com.confradestech.noorochallenge.wheatherApp.domain.WeatherInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherAppViewModel(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _weatherInfoState = MutableStateFlow(WeatherUiState())
    val weatherInfoState = _weatherInfoState
        .onStart {
            searchWeatherInfo(isFromStart = true)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            WeatherUiState()
        )

    fun onAction(actions: WeatherAppActions) {
        when (actions) {
            is WeatherAppActions.onSearchWeatherInfo -> {
                searchWeatherInfo(actions.cityName)
            }

            is WeatherAppActions.onTapLastCitySearchedCard -> {
                _weatherInfoState.value.lastCitySearchedName?.let {
                    saveLastSearchedCity(it)
                    updateLastCitySearchedCardTappedState(true)
                }
            }
        }
    }

    private fun searchWeatherInfo(
        cityName: String? = null,
        isFromStart: Boolean = false
    ) {
        updateLoadingState(true)
        viewModelScope.launch {
            cityName?.let {
                updateLastCitySearchedNameState(it)
            }
            getWeatherInfoUseCase.invoke(cityName)
                .onSuccess { weatherInfo ->
                    updateWeatherInfoState(weatherInfo)
                    if (weatherInfo != null && isFromStart) {
                        updateLastCitySearchedCardTappedState(true)
                    } else {
                        updateLastCitySearchedCardTappedState(false)
                    }
                }
                .onError { error ->
                    updateErrorState(error)
                }
        }
    }

    private fun saveLastSearchedCity(city: String) {
        viewModelScope.launch {
            preferencesManager.saveLastCity(city)
        }
    }

    private fun updateLastCitySearchedCardTappedState(isTapped: Boolean) {
        viewModelScope.launch {
            _weatherInfoState.update {
                it.copy(
                    isLastCitySearchedCardTapped = isTapped
                )
            }
        }
    }

    private fun updateLastCitySearchedNameState(cityName: String) {
        viewModelScope.launch {
            _weatherInfoState.update {
                it.copy(
                    lastCitySearchedName = cityName
                )
            }
        }
    }

    private fun updateWeatherInfoState(weatherInfo: WeatherInfo?) {
        viewModelScope.launch {
            _weatherInfoState.update {
                it.copy(
                    weatherInfo = weatherInfo,
                    error = null
                )
            }
            updateLoadingState(false)
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        viewModelScope.launch {
            _weatherInfoState.update {
                it.copy(
                    isLoading = isLoading
                )
            }
        }
    }

    private fun updateErrorState(error: NetworkError) {
        viewModelScope.launch {
            _weatherInfoState.update {
                it.copy(
                    error = error,
                    weatherInfo = null,
                    lastCitySearchedName = null,
                    isLastCitySearchedCardTapped = false
                )
            }
            updateLoadingState(false)
        }
    }
}