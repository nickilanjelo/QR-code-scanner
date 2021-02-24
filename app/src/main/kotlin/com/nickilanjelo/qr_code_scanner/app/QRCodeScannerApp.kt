package com.nickilanjelo.qr_code_scanner.app

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class QRCodeScannerApp: Application() {
    @Inject lateinit var cicerone: Cicerone<Router>
    @Inject lateinit var router: Router

    val navigationHolder
        get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: QRCodeScannerApp
            private set
    }
}