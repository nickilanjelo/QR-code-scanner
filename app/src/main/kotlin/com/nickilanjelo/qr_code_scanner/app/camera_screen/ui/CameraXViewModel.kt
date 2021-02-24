package com.nickilanjelo.qr_code_scanner.app.camera_screen.ui

import androidx.lifecycle.*
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch

class CameraXViewModel(
    private val router: Router,
    private val resultKey: String?
) : ViewModel() {

    private val _scannedString: MutableLiveData<String> = MutableLiveData()
    val scannedString: LiveData<String>
        get() = _scannedString

    fun onResult(result: String?) {
        _scannedString.value = result

        viewModelScope.launch {
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