package com.confradestech.noorochallenge.wheatherApp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfoDto(
    val condition: ConditionDto,
    @SerialName("feelslike_c")
    val feelsLikeCelcius: Double,
    val humidity: Int,
    @SerialName("temp_c")
    val tempCelcius: Double,
    val uv: Double
)