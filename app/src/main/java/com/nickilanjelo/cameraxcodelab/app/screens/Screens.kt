package com.nickilanjelo.cameraxcodelab.app.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.nickilanjelo.cameraxcodelab.app.camera_screen.ui.CameraXFragment
import com.nickilanjelo.cameraxcodelab.app.main_screen.ui.MainFragment

object Screens {
    fun MainScreen() = FragmentScreen { MainFragment() }
    fun CameraXScreen(resultKey: String) = FragmentScreen { CameraXFragment.newInstance(resultKey) }
}