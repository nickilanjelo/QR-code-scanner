package com.nickilanjelo.qr_code_scanner.app.camera_screen.ui

import androidx.lifecycle.*
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraXViewModel(
    private val router: Router,
    private val resultKey: String?
) : ViewModel() {

    fun onResult(result: String?) {
        viewModelScope.launch {
            delay(1000)
            resultKey?.let {
                router.sendResult(resultKey, result ?: "")
            }
            router.exit()
        }
    }
}

@Suppress("UNCHECKED_CAST")
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