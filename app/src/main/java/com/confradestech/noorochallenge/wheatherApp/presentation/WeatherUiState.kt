package com.confradestech.noorochallenge.wheatherApp.presentation

import com.confradestech.noorochallenge.core.domain.util.NetworkError
import com.confradestech.noorochallenge.wheatherApp.domain.WeatherInfo

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val lastCitySearchedName: String? = null,
    val isLastCitySearchedCardTapped: Boolean = false,
    val error: NetworkError? = null
)
