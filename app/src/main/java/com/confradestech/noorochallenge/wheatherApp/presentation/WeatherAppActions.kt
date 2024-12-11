package com.confradestech.noorochallenge.wheatherApp.presentation

sealed interface WeatherAppActions {
    data class onSearchWeatherInfo(val cityName: String) : WeatherAppActions
    data object onTapLastCitySearchedCard : WeatherAppActions
}