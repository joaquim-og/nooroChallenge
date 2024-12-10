package com.confradestech.noorochallenge.wheatherApp.presentation

sealed interface WeatherAppActions {
    data class onSearchWeatherInfo(val cityName: String) : WeatherAppActions
    data class onTapLastCitySearchedCard(val city: String) : WeatherAppActions
}