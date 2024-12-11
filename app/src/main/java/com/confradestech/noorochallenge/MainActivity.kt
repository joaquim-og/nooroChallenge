package com.confradestech.noorochallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.confradestech.noorochallenge.ui.theme.NooroChallengeTheme
import com.confradestech.noorochallenge.wheatherApp.presentation.WeatherAppViewModel
import com.confradestech.noorochallenge.wheatherApp.presentation.WeatherScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NooroChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: WeatherAppViewModel = koinViewModel()
                    val state by viewModel.weatherInfoState.collectAsStateWithLifecycle()

                    WeatherScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color.White),
                        weatherInfoState = state,
                        onAction = { viewModel.onAction(it) }
                    )
                }
            }
        }
    }
}