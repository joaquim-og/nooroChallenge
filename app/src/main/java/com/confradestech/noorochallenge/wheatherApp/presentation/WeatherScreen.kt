package com.confradestech.noorochallenge.wheatherApp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.confradestech.noorochallenge.R
import com.confradestech.noorochallenge.core.domain.util.NetworkError
import com.confradestech.noorochallenge.core.presentation.util.capitalizeFirstLetter
import com.confradestech.noorochallenge.core.presentation.util.getUiErrorFeedback
import com.confradestech.noorochallenge.core.presentation.util.sanitizeToValidHttps
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
        if (weatherInfoState.isLoading) {
            BuildProgressIndicator()
        }
        if (weatherInfoState.error != null) {
            BuildErrorFeedback(weatherInfoState.error)
        }
        if (weatherInfoState.weatherInfo == null) {
            BuildNoCityText()
        }
        if (
            weatherInfoState.weatherInfo != null &&
            weatherInfoState.isLastCitySearchedCardTapped == false
        ) {
            BuildWeatherInfoCard(
                weatherInfo = weatherInfoState,
                onAction = onAction
            )
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

@Composable
fun BuildProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun BuildWeatherInfoCard(
    weatherInfo: WeatherUiState,
    onAction: (WeatherAppActions) -> Unit
) {
    Spacer(Modifier.height(30.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp)
            .height(105.dp)
            .background(
                color = cardBackGround,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onAction(WeatherAppActions.onTapLastCitySearchedCard) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Weather info
            Column(
                modifier = Modifier
                    .padding(
                        start = 20.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    weatherInfo.lastCitySearchedName?.capitalizeFirstLetter().orEmpty(),
                    style = MaterialTheme.typography.labelMedium,
                    color = titleColor,
                    fontSize = 18.sp
                )
                BuildTemperatureLabel(
                    temperature = weatherInfo.weatherInfo?.tempCelcius,
                    isWeatherCard = true
                )
            }

            //Icon
            Column(
                modifier = Modifier
                    .padding(
                        end = 20.dp
                    )
            ) {
                BuildWeatherIcon(
                    iconUrl = weatherInfo.weatherInfo?.conditionIcon.orEmpty(),
                    cityName = weatherInfo.lastCitySearchedName.orEmpty(),
                    isWeatherCard = true
                )
            }
        }
    }
}

@Composable
private fun BuildTemperatureLabel(
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
            fontSize = 50.sp

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

@Composable
private fun BuildWeatherIcon(
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
private class WeatherScreenPreviewProvider :
    PreviewParameterProvider<WeatherUiState> {
    private val weatherInfo = WeatherInfo(
        conditionIcon = "https://cdn.weatherapi.com/weather/64x64/night/143.png",
        feelsLikeCelcius = 2.0,
        humidity = 100,
        tempCelcius = 20.0,
        uv = 1.0
    )
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
                lastCitySearchedName = "Mumbai",
                weatherInfo = weatherInfo,
                isLastCitySearchedCardTapped = false
            ),
            WeatherUiState(
                lastCitySearchedName = "Berlin",
                weatherInfo = weatherInfo,
                isLastCitySearchedCardTapped = true
            )
        )
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(showSystemUi = true)
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