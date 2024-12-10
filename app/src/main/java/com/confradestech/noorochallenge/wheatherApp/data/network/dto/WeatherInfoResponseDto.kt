package com.confradestech.noorochallenge.wheatherApp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfoResponseDto(
    @SerialName("current")
    val weatherInfoDto: WeatherInfoDto,
    val location: LocationDto
)