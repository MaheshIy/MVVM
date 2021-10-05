package com.mvvm.kotlin

import android.app.Application
import com.mvvm.kotlin.di.appModule
import com.mvvm.kotlin.di.databaseModule
import com.mvvm.kotlin.di.repoModule
import com.mvvm.kotlin.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, databaseModule, repoModule, viewModelModule)
        }
    }
}