package com.nickilanjelo.cameraxcodelab.app.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.nickilanjelo.cameraxcodelab.R
import com.nickilanjelo.cameraxcodelab.app.CameraXCodelabApp
import com.nickilanjelo.cameraxcodelab.app.screens.Screens
import com.nickilanjelo.cameraxcodelab.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navigator = AppNavigator(this, R.id.fragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CameraXCodelabApp.INSTANCE.router.navigateTo(Screens.MainScreen())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        CameraXCodelabApp.INSTANCE.navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()

        CameraXCodelabApp.INSTANCE.navigationHolder.removeNavigator()
    }

    override fun onBackPressed() {
        CameraXCodelabApp.INSTANCE.router.exit()
    }
}