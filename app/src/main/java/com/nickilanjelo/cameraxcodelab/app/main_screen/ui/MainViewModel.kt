package com.nickilanjelo.cameraxcodelab.app.main_screen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.nickilanjelo.cameraxcodelab.app.screens.Screens

class MainViewModel(
    private val router: Router
) : ViewModel() {
    private var _scanResult: MutableLiveData<String> = MutableLiveData()
    val scanResult: LiveData<String>
        get() = _scanResult

    fun onResultReceived(result: String?) {
        _scanResult.value = result ?: ""
    }

    fun openCamera(resultKey: String) {
        router.navigateTo(Screens.CameraXScreen(resultKey))
    }
}

class MainViewModelFactory(
    private val router: Router
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(router) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}