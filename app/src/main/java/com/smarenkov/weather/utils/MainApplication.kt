package com.smarenkov.weather.utils

import android.app.Application
import com.google.gson.Gson
import com.smarenkov.weather.fragments.home.HomeViewModel
import com.smarenkov.weather.network.repository.WeatherDataRepository
import com.smarenkov.weather.storage.AppSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/*
 * Application class for the app. It initializes Koin for dependency injection.
 * https://insert-koin.io/docs/quickstart/android/
 */
class MainApplication : Application() {

    private val serializerModule = module {
        single { Gson() }
    }

    private val storageModule = module {
        single { AppSharedPreferences(context = get(), gson = get()) }
    }

    private val repositoryModule = module {
        single { WeatherDataRepository() }
    }

    private val viewModelModule = module {
        viewModel { HomeViewModel(weatherDataRepository = get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(serializerModule, storageModule, repositoryModule, viewModelModule))
        }
    }
}