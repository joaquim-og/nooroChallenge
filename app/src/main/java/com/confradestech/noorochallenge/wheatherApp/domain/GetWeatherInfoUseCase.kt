package com.confradestech.noorochallenge.wheatherApp.domain

import com.confradestech.noorochallenge.core.data.local.PreferencesManager
import com.confradestech.noorochallenge.core.domain.util.NetworkError
import com.confradestech.noorochallenge.core.domain.util.Result

class GetWeatherInfoUseCase(
    private val weatherDataSource: WeatherDataSource,
    private val preferencesManager: PreferencesManager
) {

    suspend operator fun invoke(cityName: String?): Result<WeatherInfo, NetworkError> {
        //TODO
        // add local city name check
        return weatherDataSource.getCityWeather("Paris")
    }


}