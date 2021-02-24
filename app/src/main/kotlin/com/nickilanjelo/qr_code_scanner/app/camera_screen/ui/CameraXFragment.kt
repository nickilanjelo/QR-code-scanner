package com.nickilanjelo.qr_code_scanner.app.camera_screen.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.nickilanjelo.qr_code_scanner.app.QRCodeScannerApp
import com.nickilanjelo.qr_code_scanner.app.camera_screen.analyzer.QRCodeAnalyzer
import com.nickilanjelo.qrcodescanner.databinding.FragmentCameraXBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class CameraXFragment: Fragment() {

    companion object {
        private const val RESULT_KEY_TAG = "RESULT_KEY_TAG"

        fun newInstance(resultKey: String): CameraXFragment {
            return CameraXFragment().also {
                it.arguments = bundleOf(RESULT_KEY_TAG to resultKey)
            }
        }
    }

    @Inject lateinit var scanner: BarcodeScanner
    private lateinit var cameraExecutor: ExecutorService

    private var _binding: FragmentCameraXBinding? = null
    private val binding: FragmentCameraXBinding
        get() = _binding!!

    private val viewModel: CameraXViewModel by viewModels {
        CameraXViewModelFactory(QRCodeScannerApp.INSTANCE.router, arguments?.getString(RESULT_KEY_TAG))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCameraXBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCamera()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    private fun startCamera() {
        context?.let {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(it)

            cameraProviderFuture.addListener(
                {
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder()
                        .build()
                        .also { preview ->
                            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                        }

                    val imageCapture = ImageCapture.Builder()
                        .build()

                    val imageAnalyzer = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also { analysis ->
                            analysis.setAnalyzer(cameraExecutor, QRCodeAnalyzer(scanner) { barcode ->
                                viewModel.onResult(barcode.rawValue)
                            })
                        }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            this, cameraSelector, preview, imageCapture, imageAnalyzer)

                    } catch(error: Throwable) {
                        Log.e(this::class.simpleName, "Use case binding failed", error)
                    }

                }, ContextCompat.getMainExecutor(it)
            )
        }
    }
}