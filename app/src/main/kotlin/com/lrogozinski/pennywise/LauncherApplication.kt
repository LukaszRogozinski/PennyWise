package com.lrogozinski.pennywise

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class LauncherApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.FLAVOR == "dev") {
            Timber.plant(Timber.DebugTree())
        }
    }
}
