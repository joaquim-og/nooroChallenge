package com.confradestech.noorochallenge.wheatherApp.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.confradestech.noorochallenge.R
import com.confradestech.noorochallenge.core.presentation.util.sanitizeToValidHttps
import com.confradestech.noorochallenge.ui.theme.NooroChallengeTheme

@Composable
fun WeatherIcon(
    iconUrl: String,
    cityName: String,
    isWeatherCard: Boolean
) {
    AsyncImage(
        modifier = Modifier.then(
            if (isWeatherCard) {
                Modifier
                    .width(67.dp)
                    .height(83.dp)
            } else {
                Modifier.size(123.dp)
            }
        ),
        model = iconUrl.sanitizeToValidHttps(),
        contentDescription = stringResource(R.string.temperature_icon_description).replace(
            "#value",
            cityName
        ),
        placeholder = painterResource(R.drawable.baseline_image_search_24),
        error = painterResource(R.drawable.baseline_broken_image_24),
    )
}

//region previews
@Composable
@Preview()
private fun WeatherIconWeatherCardFalsePreview() {
    NooroChallengeTheme {
        WeatherIcon(
            iconUrl = "https://cdn.weatherapi.com/weather/64x64/night/143.png",
            cityName = "Paris",
            isWeatherCard = false
        )
    }
}

@Composable
@Preview()
private fun WeatherIconWeatherCardTruePreview() {
    NooroChallengeTheme {
        WeatherIcon(
            iconUrl = "https://cdn.weatherapi.com/weather/64x64/night/143.png",
            cityName = "Paris",
            isWeatherCard = true
        )
    }
}
//endregion previews