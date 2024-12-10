package com.confradestech.noorochallenge.di

import com.confradestech.noorochallenge.core.data.local.PreferencesManager
import com.confradestech.noorochallenge.core.data.network.HttpClientFactory
import com.confradestech.noorochallenge.wheatherApp.data.network.RemoteWeatherDataSource
import com.confradestech.noorochallenge.wheatherApp.domain.GetWeatherInfoUseCase
import com.confradestech.noorochallenge.wheatherApp.domain.WeatherDataSource
import com.confradestech.noorochallenge.wheatherApp.presentation.WeatherAppViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single { PreferencesManager(get()) }
    single { GetWeatherInfoUseCase(get(), get()) }
    viewModelOf(::WeatherAppViewModel)
    singleOf(::RemoteWeatherDataSource).bind<WeatherDataSource>()
}
