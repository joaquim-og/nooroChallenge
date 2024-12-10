package com.confradestech.noorochallenge.wheatherApp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.confradestech.noorochallenge.R
import com.confradestech.noorochallenge.core.domain.util.NetworkError
import com.confradestech.noorochallenge.core.presentation.util.getUiErrorFeedback
import com.confradestech.noorochallenge.ui.theme.NooroChallengeTheme
import com.confradestech.noorochallenge.ui.theme.cardBackGround
import com.confradestech.noorochallenge.ui.theme.inputFieldColor
import com.confradestech.noorochallenge.ui.theme.titleColor
import com.confradestech.noorochallenge.wheatherApp.domain.WeatherInfo


@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherInfoState: WeatherUiState,
    onAction: (WeatherAppActions) -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                vertical = 22.dp,
                horizontal = 12.dp
            )
    ) {
        BuildSearchBar(
            onAction = onAction
        )
        if (weatherInfoState.weatherInfo == null && weatherInfoState.error == null) {
            BuildNoCityText()
        }
        if (weatherInfoState.weatherInfo == null && weatherInfoState.error != null) {
            BuildErrorFeedback(weatherInfoState.error)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuildSearchBar(
    onAction: (WeatherAppActions) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    Box(Modifier.fillMaxWidth()) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            inputField = {
                InputField(
                    modifier = Modifier.background(
                        color = cardBackGround,
                        shape = RoundedCornerShape(16.dp)
                    ),
                    colors = inputFieldColors(
                        focusedTextColor = titleColor,
                        unfocusedTextColor = titleColor,
                        focusedPlaceholderColor = inputFieldColor,
                        unfocusedPlaceholderColor = inputFieldColor,
                        focusedTrailingIconColor = inputFieldColor,
                        unfocusedTrailingIconColor = inputFieldColor
                    ),
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { onAction(WeatherAppActions.onSearchWeatherInfo(text)) },
                    expanded = false,
                    onExpandedChange = { },
                    placeholder = {
                        Text(
                            stringResource(R.string.weatherScreen_search_bar_hint),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    },
                    trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                )
            },
            expanded = false,
            onExpandedChange = { /*no-op*/ },
        ) { /*No-op*/ }
    }
}

@Composable
private fun BuildNoCityText() {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.weatherScreen_no_city_selected_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            stringResource(R.string.weatherScreen_no_city_selected_subtitle),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun BuildErrorFeedback(error: NetworkError) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.error_generic),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(
                horizontal = 12.dp
            ),
            text = error.getUiErrorFeedback(context),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

//region previews
private class WeatherScreenPreviewProvider :
    PreviewParameterProvider<WeatherUiState> {
    override val values: Sequence<WeatherUiState>
        get() = sequenceOf(
            WeatherUiState(),
            WeatherUiState(
                error = NetworkError.NO_MATCHING_LOCATION
            ),
            WeatherUiState(
                isLoading = true
            ),
            WeatherUiState(
                weatherInfo = WeatherInfo(
                    conditionIcon = "https://api.weatherapi.com//cdn.weatherapi.com/weather/64x64/night/266.png",
                    feelsLikeCelcius = 2.0,
                    humidity = 100,
                    tempCelcius = 5.3,
                    uv = 1.0
                )
            )
        )
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
private fun WeatherScreenPreview(
    @PreviewParameter(WeatherScreenPreviewProvider::class) weatherScreenPreviewProvider: WeatherUiState
) {
    NooroChallengeTheme {
        WeatherScreen(
            weatherInfoState = weatherScreenPreviewProvider,
            onAction = { /*NO-OP for previews */ }
        )
    }
}
//endregion previews