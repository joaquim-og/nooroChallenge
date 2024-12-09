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
            searchWeatherInfo()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            WeatherUiState()
        )

    fun searchWeatherInfo(cityName: String? = null) {
        viewModelScope.launch {
            updateLoadingState(true)
            cityName?.let {
                updateLastCitySearchedNameState(it)
            }
            getWeatherInfoUseCase.invoke(cityName)
                .onSuccess { weatherInfo ->
                    updateWeatherInfoState(weatherInfo)
                }
                .onError { error ->
                    updateErrorState(error)
                }
        }
    }

    //TODO
    // call when user tap the city weather mini card
    fun saveLastSearchedCity(city: String) {
        viewModelScope.launch {
            preferencesManager.saveLastCity(city)
        }
    }

    //TODO
    // call when user tap the city weather mini card
    fun updateLastCitySearchedCardTappedState() {
        viewModelScope.launch {
            _weatherInfoState.update {
                it.copy(
                    isLastCitySearchedCardTapped = !it.isLastCitySearchedCardTapped
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
                    error = error
                )
            }
            updateLoadingState(false)
        }
    }
}