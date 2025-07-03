package com.jetbrains.fintechpayment

import android.app.Application
import com.jetbrains.fintechpayment.di.appModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.logger.PrintLogger

class FintechApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger(PrintLogger())
            androidContext(this@FintechApp)
            modules(appModule)
        }
    }
}
