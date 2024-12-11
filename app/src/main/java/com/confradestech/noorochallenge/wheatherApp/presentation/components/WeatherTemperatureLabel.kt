package com.confradestech.noorochallenge.wheatherApp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.confradestech.noorochallenge.R
import com.confradestech.noorochallenge.ui.theme.NooroChallengeTheme
import com.confradestech.noorochallenge.ui.theme.titleColor

@Composable
fun WeatherTemperatureLabel(
    temperature: Double?,
    isWeatherCard: Boolean
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = temperature?.toInt().toString(),
            style = MaterialTheme.typography.labelSmall,
            color = titleColor,
            fontSize = if (isWeatherCard) {
                50.sp
            } else {
                80.sp
            }
        )
        Text(
            modifier = Modifier.padding(
                top = 10.dp,
                start = if (isWeatherCard) {
                    10.dp
                } else {
                    5.dp
                },
            ),
            text = stringResource(R.string.temperature_label),
            style = MaterialTheme.typography.labelSmall,
            color = titleColor,
            fontSize = if (isWeatherCard) {
                15.sp
            } else {
                20.sp
            }
        )
    }
}

//region previews
@Composable
@Preview()
private fun WeatherTemperatureLabelWeatherCardFalsePreview() {
    NooroChallengeTheme {
        WeatherTemperatureLabel(
            temperature = 2.0,
            isWeatherCard = false
        )
    }
}

@Composable
@Preview()
private fun WeatherTemperatureLabelWeatherCardTruePreview() {
    NooroChallengeTheme {
        WeatherTemperatureLabel(
            temperature = 2.0,
            isWeatherCard = true
        )
    }
}
//endregion previews