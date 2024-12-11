package com.confradestech.noorochallenge.wheatherApp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.confradestech.noorochallenge.R
import com.confradestech.noorochallenge.ui.theme.NooroChallengeTheme

@Composable
fun WeatherDetailsTexts(
    title: String,
    value: String
) {
    Column(
        modifier = Modifier.width(60.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            softWrap = false,
            text = value,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}

//region previews
@Composable
@Preview()
private fun WeatherDetailsTextsPreview() {
    NooroChallengeTheme {
        WeatherDetailsTexts(
            title = stringResource(R.string.weatherScreen_details_UV),
            value = "20"
        )
    }
}
//endregion previews