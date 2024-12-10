package com.confradestech.noorochallenge.wheatherApp.presentation

import com.confradestech.noorochallenge.core.domain.util.NetworkError
import com.confradestech.noorochallenge.wheatherApp.domain.WeatherInfo

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val error: NetworkError? = null
)
