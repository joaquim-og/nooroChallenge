package com.confradestech.noorochallenge.wheatherApp.data.mappers

import com.confradestech.noorochallenge.wheatherApp.data.network.dto.WeatherInfoDto
import com.confradestech.noorochallenge.wheatherApp.domain.WeatherInfo

fun WeatherInfoDto.toWeatherInfo(cityName: String) = WeatherInfo(
    conditionIcon = condition.icon,
    feelsLikeCelcius = feelsLikeCelcius,
    humidity = humidity,
    tempCelcius = tempCelcius,
    uv = uv,
    cityName = cityName
)