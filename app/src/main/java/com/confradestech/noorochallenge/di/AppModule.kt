package com.confradestech.noorochallenge.di

import com.confradestech.noorochallenge.core.data.network.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val appModule = module {

    //TODO
    // add modules
    single { HttpClientFactory.create(CIO.create()) }

}
