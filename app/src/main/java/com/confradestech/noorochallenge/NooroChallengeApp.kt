package com.confradestech.noorochallenge

import android.app.Application
import com.confradestech.noorochallenge.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class NooroChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NooroChallengeApp)
            androidLogger()
            modules(appModule)
        }
    }
}