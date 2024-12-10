package com.confradestech.noorochallenge.wheatherApp.domain

import com.confradestech.noorochallenge.core.data.local.PreferencesManager
import com.confradestech.noorochallenge.core.domain.util.NetworkError
import com.confradestech.noorochallenge.core.domain.util.Result
import kotlinx.coroutines.flow.first

class GetWeatherInfoUseCase(
    private val weatherDataSource: WeatherDataSource,
    private val preferencesManager: PreferencesManager
) {

    suspend operator fun invoke(cityName: String?): Result<WeatherInfo?, NetworkError> {
        val city = cityName ?: preferencesManager.getLastCity.first()

        return if (city.isNullOrEmpty()) {
            Result.Success(null)
        } else {
            weatherDataSource.getCityWeather(city)
        }
    }
}