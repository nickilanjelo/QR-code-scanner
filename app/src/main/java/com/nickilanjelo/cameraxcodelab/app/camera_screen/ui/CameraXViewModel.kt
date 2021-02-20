package com.nickilanjelo.cameraxcodelab.app.camera_screen.ui

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.nickilanjelo.cameraxcodelab.app.camera_screen.analyzer.QRCodeAnalyzer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraXViewModel(
    private val router: Router,
    private val resultKey: String?
) : ViewModel() {

    fun onResult(result: String?) {
        viewModelScope.launch {
            delay(2000)
            resultKey?.let {
                router.sendResult(resultKey, result ?: "")
            }
            router.exit()
        }
    }
}

class CameraXViewModelFactory(
    private val router: Router,
    private val resultKey: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraXViewModel::class.java)) {
            return CameraXViewModel(router, resultKey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}