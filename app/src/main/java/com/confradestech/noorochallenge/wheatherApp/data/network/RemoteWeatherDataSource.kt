package com.confradestech.noorochallenge.wheatherApp.data.network

import com.confradestech.noorochallenge.BuildConfig
import com.confradestech.noorochallenge.core.data.network.safeCall
import com.confradestech.noorochallenge.core.domain.util.NetworkError
import com.confradestech.noorochallenge.core.domain.util.Result
import com.confradestech.noorochallenge.core.domain.util.map
import com.confradestech.noorochallenge.wheatherApp.data.mappers.toWeatherInfo
import com.confradestech.noorochallenge.wheatherApp.data.network.dto.WeatherInfoResponseDto
import com.confradestech.noorochallenge.wheatherApp.domain.WeatherDataSource
import com.confradestech.noorochallenge.wheatherApp.domain.WeatherInfo
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteWeatherDataSource(
    private val httpClient: HttpClient
) : WeatherDataSource {
    override suspend fun getCityWeather(cityName: String): Result<WeatherInfo, NetworkError> {
        return safeCall<WeatherInfoResponseDto> {
            httpClient.get(
                urlString = "${BuildConfig.BASE_URL}${BuildConfig.WEATHER_API_KEY}&q=$cityName"
            )
        }.map { response ->
            response.weatherInfoDto.toWeatherInfo(cityName)
        }
    }
}