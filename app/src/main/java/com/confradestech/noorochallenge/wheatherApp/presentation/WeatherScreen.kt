package com.confradestech.noorochallenge.wheatherApp.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun WeatherScreen(
    weatherInfoState: WeatherUiState
) {
    Text(
        text = "${weatherInfoState.weatherInfo}"
    )
}