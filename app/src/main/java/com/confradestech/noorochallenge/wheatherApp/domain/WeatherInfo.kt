package com.confradestech.noorochallenge.wheatherApp.domain

data class WeatherInfo(
    val conditionIcon: String,
    val feelsLikeCelcius: Double,
    val humidity: Int,
    val tempCelcius: Double,
    val uv: Double
)
