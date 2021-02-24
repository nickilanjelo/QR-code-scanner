package com.nickilanjelo.qr_code_scanner.app.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.nickilanjelo.qrcodescanner.R
import com.nickilanjelo.qr_code_scanner.app.QRCodeScannerApp
import com.nickilanjelo.qr_code_scanner.app.screens.Screens
import com.nickilanjelo.qrcodescanner.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navigator = AppNavigator(this, R.id.fragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        QRCodeScannerApp.INSTANCE.router.replaceScreen(Screens.MainScreen())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        QRCodeScannerApp.INSTANCE.navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()

        QRCodeScannerApp.INSTANCE.navigationHolder.removeNavigator()
    }

    override fun onBackPressed() {
        QRCodeScannerApp.INSTANCE.router.exit()
    }
}