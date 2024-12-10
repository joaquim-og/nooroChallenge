package com.confradestech.noorochallenge.wheatherApp.domain

import com.confradestech.noorochallenge.core.domain.util.NetworkError
import com.confradestech.noorochallenge.core.domain.util.Result

interface WeatherDataSource {
    suspend fun getCityWeather(cityName: String): Result<WeatherInfo, NetworkError>
}