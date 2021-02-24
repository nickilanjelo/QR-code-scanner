package com.nickilanjelo.qr_code_scanner.app.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.nickilanjelo.qr_code_scanner.app.camera_screen.ui.CameraXFragment
import com.nickilanjelo.qr_code_scanner.app.main_screen.ui.MainFragment

object Screens {
    fun MainScreen() = FragmentScreen { MainFragment() }
    fun CameraXScreen(resultKey: String) = FragmentScreen { CameraXFragment.newInstance(resultKey) }
}