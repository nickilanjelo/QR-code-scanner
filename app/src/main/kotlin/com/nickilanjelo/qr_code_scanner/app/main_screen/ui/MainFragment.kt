package com.nickilanjelo.qr_code_scanner.app.main_screen.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nickilanjelo.qr_code_scanner.app.QRCodeScannerApp
import com.nickilanjelo.qrcodescanner.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {

    companion object {
        private const val SCANNING_RESULT_TAG = "SCANNING_RESULT_TAG"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            router = QRCodeScannerApp.INSTANCE.router
        )
    }

    private val getPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.all { permission ->
                permission.value == true
            }) {
            viewModel.openCamera(SCANNING_RESULT_TAG)
        } else {
            showError()
        }
    }

    private fun showError() {
        Toast.makeText(context, "Отсутствует разрешение на использование камеры", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        QRCodeScannerApp.INSTANCE.router.setResultListener(SCANNING_RESULT_TAG) { result ->
            viewModel.onResultReceived(result as String)
        }

        viewModel.scanResult.observe(viewLifecycleOwner) { result ->
            binding.txtVwResult.text = result
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnCamera.setOnClickListener {
                checkPermissions()
            }
        }
    }

    private fun checkPermissions() {
        if (isCameraPermissionGranted) {
            viewModel.openCamera(SCANNING_RESULT_TAG)
        } else {
            grantPermissions()
        }
    }

    private fun grantPermissions() {
        getPermissions.launch(REQUIRED_PERMISSIONS)
    }

    private val isCameraPermissionGranted = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(QRCodeScannerApp.INSTANCE.baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}